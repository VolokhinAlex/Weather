package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Callback

/**
 * The remote repository to get the weather of cities list
 */

interface MainRepository {

    fun getWeatherFromRemoteServer(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    )

}