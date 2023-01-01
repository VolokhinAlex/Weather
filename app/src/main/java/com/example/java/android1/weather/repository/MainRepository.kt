package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Callback

interface MainRepository {

    fun getWeatherFromRemoteSource(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    )

}