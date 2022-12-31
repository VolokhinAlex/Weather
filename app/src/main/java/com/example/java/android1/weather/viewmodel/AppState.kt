package com.example.java.android1.weather.viewmodel

import com.example.java.android1.weather.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}