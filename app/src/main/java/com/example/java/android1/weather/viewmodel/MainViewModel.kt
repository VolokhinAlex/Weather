package com.example.java.android1.weather.viewmodel

import android.location.Geocoder
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.*
import com.example.java.android1.weather.app.App
import com.example.java.android1.weather.app.LocationState
import com.example.java.android1.weather.app.WeatherAppState
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.repository.*
import com.example.java.android1.weather.view.LanguageQuery
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    private val remoteRepository: MainRepository,
    private val localRepository: WeatherLocalRepository
) : ViewModel() {
    private val coordinationHandlerThreadName = "CoordinationHandlerThread"

    private val _weatherData: MutableLiveData<WeatherAppState> = MutableLiveData()
    val weatherData: LiveData<WeatherAppState> = _weatherData
    private val _weatherLocationData: MutableLiveData<LocationState> = MutableLiveData()
    val weatherLocationData: LiveData<LocationState> = _weatherLocationData

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse = response.body()
            _weatherData.value = if (serverResponse != null && response.isSuccessful) {
                WeatherAppState.Success(listOf(serverResponse))
            } else {
                WeatherAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
            _weatherData.value = WeatherAppState.Error(error)
        }
    }

    /**
     * The data gets from the Room Database if this data is not empty
     */

    fun getWeatherFromLocalDataBase() {
        _weatherData.value = WeatherAppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getAllWeatherList()
            _weatherData.postValue(WeatherAppState.Success(resultLocalRequest))
        }
    }

    /**
     * The data gets from Remote Repository using Retrofit. The data gets from the Yandex Weather API
     */

    fun getWeatherCityFromRemoteServer(latitude: Double, longitude: Double, lang: String) {
        remoteRepository.getWeatherFromRemoteServer(
            latitude,
            longitude,
            lang,
            callback
        )
    }

    /**
     * The method is called from the search field in the MainScreen
     */

    fun getCoordinationByCity(geocoder: Geocoder, query: String) {
        val coordinationHandlerThread = HandlerThread(coordinationHandlerThreadName)
        coordinationHandlerThread.start()
        val handler = Handler(coordinationHandlerThread.looper)
        handler.post {
            val addresses = geocoder.getFromLocationName(query, 10)
            if (addresses != null && addresses.size != 0) {
                remoteRepository.getWeatherFromRemoteServer(
                    addresses[0].latitude,
                    addresses[0].longitude,
                    LanguageQuery.EN.languageQuery,
                    callback
                )
            }
        }
    }

    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        _weatherLocationData.value = LocationState.Success(latitude, longitude)
    }

    fun getWeatherByLocationError(message: String) {
        _weatherLocationData.value = LocationState.NotEnabledGPS(message)
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(
                MainRepositoryImpl(RemoteDataSource()),
                WeatherLocalRepositoryImpl(App.weather_dao)
            ) as T
        } else {
            throw IllegalArgumentException("MainViewModel not found")
        }
    }
}