package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * The interface for interacting with Yandex API.
 */

interface WeatherApi {

    /**
     * The method for getting a details weather of the city
     */

    @GET("v2/forecast")
    fun getDetailsWeather(
        @Header("X-Yandex-API-Key") token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ) : Call<WeatherDTO>

}