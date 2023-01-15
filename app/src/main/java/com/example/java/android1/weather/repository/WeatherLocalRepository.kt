package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO

/**
 * The local repository for interacting with weather local database
 */

interface WeatherLocalRepository {
    fun getAllWeatherList(): List<WeatherDTO>

    suspend fun insertWeather(weatherDTO: WeatherDTO)

    suspend fun updateWeather(weatherDTO: WeatherDTO)
}