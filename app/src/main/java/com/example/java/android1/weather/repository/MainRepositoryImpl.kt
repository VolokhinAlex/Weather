package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.*
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from Remote API
 */

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    override fun getWeatherFromRemoteServer(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getDetailsWeather(lat, lon, lang, callback)
    }

}