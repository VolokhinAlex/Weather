package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO

interface MainRepository {

    fun getWeatherFromLocalStorageRus(): List<WeatherDTO>
    fun getWeatherFromLocalStorageWorld(): List<WeatherDTO>

}