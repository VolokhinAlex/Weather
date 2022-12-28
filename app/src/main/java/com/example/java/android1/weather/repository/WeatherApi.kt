package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {

    @GET("v2/forecast")
    fun getWeatherDetail(
        @Header("X-Yandex-API-Key") token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ) : Call<WeatherDTO>

}