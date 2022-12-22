package com.example.java.android1.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.weather.repository.MainRepository
import com.example.java.android1.weather.repository.MainRepositoryImpl
import com.example.java.android1.weather.repository.RemoteDataSource

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: MainRepository = MainRepositoryImpl(RemoteDataSource())
) :
    ViewModel() {

    init {
        getWeatherFromLocalSourceRus()
    }

    val liveDataSource: LiveData<AppState>
        get() = liveData

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            val weatherData = when (isRussian) {
                true -> repository.getWeatherFromLocalStorageRus()
                false -> repository.getWeatherFromLocalStorageWorld()
            }
            liveData.postValue(AppState.Success(weatherData))
        }.start()
    }

}