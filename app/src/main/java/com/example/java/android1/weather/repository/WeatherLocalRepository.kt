package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO

interface WeatherLocalRepository {

    fun getAllWeather(): List<WeatherDTO>

    suspend fun insertWeather(weatherDTO: WeatherDTO)

    suspend fun update(weatherDTO: WeatherDTO)
}