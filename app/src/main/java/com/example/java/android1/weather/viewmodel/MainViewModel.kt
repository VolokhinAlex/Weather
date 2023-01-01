package com.example.java.android1.weather.viewmodel

import android.location.Geocoder
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.weather.app.App
import com.example.java.android1.weather.app.AppState
import com.example.java.android1.weather.app.LocationState
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val localRepository: WeatherLocalRepository = WeatherLocalRepositoryImpl(App.weather_dao),
    private val remoteRepository: MainRepository = MainRepositoryImpl(RemoteDataSource()),
    val locationState: MutableLiveData<LocationState> = MutableLiveData()
) : ViewModel() {

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse = response.body()
            liveData.value = if (serverResponse != null && response.isSuccessful) {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.insertWeather(serverResponse)
                }
                AppState.Success(listOf(serverResponse))
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
            liveData.value = AppState.Error(error)
        }
    }

    init {
        getWeatherFromLocalDataBase()
    }

    /**
     * The data gets from the Room Database if this data is not empty
     */

    fun getWeatherFromLocalDataBase() {
        liveData.value = AppState.Loading
        val handler = android.os.Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getAllWeather()
            handler.post {
                liveData.value = AppState.Success(resultLocalRequest)
            }
        }
    }

    /**
     * The data gets from Remote Repository using Retrofit. The data gets from the Yandex Weather API
     */

    fun getWeatherCityFromRemoteSource(latitude: Double, longitude: Double, lang: String) {
        remoteRepository.getWeatherFromRemoteSource(
            latitude,
            longitude,
            lang,
            callback
        )
    }

    /**
     * The method is called from the search field in the MainScreen
     */

    fun getCityCoordination(geocoder: Geocoder, query: String) {
        Thread {
            val addresses = geocoder.getFromLocationName(query, 10)
            if (addresses != null && addresses.size != 0) {
                remoteRepository.getWeatherFromRemoteSource(
                    addresses[0].latitude,
                    addresses[0].longitude,
                    "ru_RU",
                    callback
                )
            }
        }.start()
    }

    fun getLocationSuccess(latitude: Double, longitude: Double) {
        locationState.value = LocationState.Success(latitude, longitude)
    }

    fun getLocationNotEnabledGPS(message: String) {
        locationState.value = LocationState.NotEnabledGPS(message)
    }

}