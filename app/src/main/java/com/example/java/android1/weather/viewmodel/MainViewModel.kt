package com.example.java.android1.weather.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.weather.app.App
import com.example.java.android1.weather.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val localRepository: WeatherLocalRepository = WeatherLocalRepositoryImpl(App.weather_dao),
    private val remoteRepository: MainRepository = MainRepositoryImpl(RemoteDataSource())
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
            if (resultLocalRequest.isEmpty()) {
                handler.post {
                    liveData.value =
                        AppState.Success(remoteRepository.getWeatherFromLocalStorageRus())
                }
            } else {
                handler.post {
                    liveData.value = AppState.Success(resultLocalRequest)
                }
            }
        }
    }

    private fun getWeatherFromLocalDataBaseWorld() {
        liveData.value = AppState.Loading
        val handler = android.os.Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val resultLocalRequest = localRepository.getWeatherFromLocalStorageWorld()
            if (resultLocalRequest.isEmpty()) {
                handler.post {
                    liveData.value =
                        AppState.Success(remoteRepository.getWeatherFromLocalStorageWorld())
                }
            } else {
                handler.post {
                    liveData.value = AppState.Success(resultLocalRequest)
                }
            }
        }
    }

}