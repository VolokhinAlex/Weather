package com.example.java.android1.weather.viewmodel

import com.example.java.android1.weather.model.WeatherDTO

sealed class AppState {
    data class Success(val weatherData: List<WeatherDTO>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}