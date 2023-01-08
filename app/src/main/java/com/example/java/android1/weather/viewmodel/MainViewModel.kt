package com.example.java.android1.weather.viewmodel

import android.location.Geocoder
import androidx.lifecycle.*
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
    private val remoteRepository: MainRepository,
    private val localRepository: WeatherLocalRepository
) : ViewModel() {

    private val _weatherData: MutableLiveData<AppState> = MutableLiveData()
    val weatherData: LiveData<AppState> = _weatherData
    private val _weatherLocationData: MutableLiveData<LocationState> = MutableLiveData()
    val weatherLocationData: LiveData<LocationState> = _weatherLocationData

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse = response.body()
            _weatherData.value = if (serverResponse != null && response.isSuccessful) {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.insertWeather(serverResponse)
                }
                AppState.Success(listOf(serverResponse))
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
            _weatherData.value = AppState.Error(error)
        }
    }

    init {
        getWeatherFromLocalDataBase()
    }

    /**
     * The data gets from the Room Database if this data is not empty
     */

    fun getWeatherFromLocalDataBase() {
        _weatherData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getAllWeather()
            _weatherData.postValue(AppState.Success(resultLocalRequest))
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

    fun getCoordinationByCity(geocoder: Geocoder, query: String) {
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

    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        _weatherLocationData.value = LocationState.Success(latitude, longitude)
    }

    fun getWeatherByLocationError(message: String) {
        _weatherLocationData.value = LocationState.NotEnabledGPS(message)
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: MainRepository,
    private val localRepository: WeatherLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository, localRepository) as T
        } else {
            throw IllegalArgumentException("MainViewModel not found")
        }
    }
}