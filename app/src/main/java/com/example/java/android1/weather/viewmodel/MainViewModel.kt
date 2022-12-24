package com.example.java.android1.weather.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.weather.app.App
import com.example.java.android1.weather.repository.WeatherLocalRepository
import com.example.java.android1.weather.repository.WeatherLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val localRepository: WeatherLocalRepository = WeatherLocalRepositoryImpl(App.weather_dao)
) : ViewModel() {

    init {
        getWeatherFromLocalDataBaseRus()
    }

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalDataBaseRus()

    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalDataBaseWorld()

    private fun getWeatherFromLocalDataBaseRus() {
        liveData.value = AppState.Loading
        val handler = android.os.Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getWeatherFromLocalStorageRus()
            handler.post {
                liveData.value = AppState.Success(resultLocalRequest)
            }
        }
    }

    private fun getWeatherFromLocalDataBaseWorld() {
        liveData.value = AppState.Loading
        val handler = android.os.Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getWeatherFromLocalStorageWorld()
            handler.post {
                liveData.value = AppState.Success(resultLocalRequest)
            }
        }
    }

}