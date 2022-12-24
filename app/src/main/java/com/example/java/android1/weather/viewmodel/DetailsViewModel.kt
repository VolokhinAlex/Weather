package com.example.java.android1.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.weather.app.App.Companion.weather_dao
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val localRepository: WeatherLocalRepository = WeatherLocalRepositoryImpl(weather_dao)
) : ViewModel() {

    private val callback = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse = response.body()
            detailsLiveData.value = if (serverResponse != null && response.isSuccessful) {
                insertWeatherToLocalBase(serverResponse)
                AppState.Success(listOf(serverResponse))
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
            detailsLiveData.value = AppState.Error(error)
        }
    }

    fun getWeatherDetailFromRemoteServer(lat: Double, lon: Double, lang: String) {
        detailsLiveData.value = AppState.Loading
        repository.getWeatherDetailFromServer(lat, lon, lang, callback)
    }

    private fun insertWeatherToLocalBase(weatherData: WeatherDTO) =
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertWeather(weatherData)
        }

}