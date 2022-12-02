package com.example.java.android1.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.weather.model.Repository
import com.example.java.android1.weather.model.RepositoryImpl
import java.util.Random
import java.util.concurrent.Executors

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Executors.newFixedThreadPool(5).submit {
            Thread.sleep(1000)
            liveData.postValue(randomData())
        }
    }

    private fun randomData(): AppState {
        return when (Random().nextInt(3)) {
            0 -> AppState.Success(repository.getWeatherFromLocalStorage())
            1 -> AppState.Error(Throwable("The city wasn't found"))
            2 -> AppState.Loading
            else -> AppState.Loading
        }
    }

}