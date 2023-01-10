package com.example.java.android1.weather.viewmodel

import androidx.lifecycle.*
import com.example.java.android1.weather.app.WeatherAppState
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class DetailsViewModel(
    private val detailsRepository: DetailsRepository,
    private val localRepository: WeatherLocalRepository
) : ViewModel() {

    private val _detailsWeatherData: MutableLiveData<WeatherAppState> = MutableLiveData()
    val detailsWeatherData: LiveData<WeatherAppState> = _detailsWeatherData

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse = response.body()
            _detailsWeatherData.value = if (serverResponse != null && response.isSuccessful) {
                insertWeatherToLocalBase(serverResponse)
                WeatherAppState.Success(listOf(serverResponse))
            } else {
                WeatherAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
            _detailsWeatherData.value = WeatherAppState.Error(error)
        }
    }

    /**
     * The data gets from Remote Repository using Retrofit. The data gets from the Yandex Weather API
     */

    fun getWeatherDetailsFromRemoteServer(lat: Double, lon: Double, lang: String) {
        _detailsWeatherData.value = WeatherAppState.Loading
        detailsRepository.getWeatherDetailsFromRemoteServer(lat, lon, lang, callback)
    }

    /**
     * The method writes the city's weather data to a local database.
     */

    private fun insertWeatherToLocalBase(weatherData: WeatherDTO) =
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertWeather(weatherData)
        }

}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val detailsRepository: DetailsRepository,
    private val localRepository: WeatherLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(detailsRepository, localRepository) as T
        } else {
            throw IllegalArgumentException("DetailsViewModel not found")
        }
    }
}