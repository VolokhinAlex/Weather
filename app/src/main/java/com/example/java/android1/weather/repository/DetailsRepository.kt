package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Callback

interface DetailsRepository {

    fun getWeatherDetailFromServer(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    )

}