package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from Remote API
 */

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    override fun getWeatherDetailsFromRemoteServer(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getDetailsWeather(lat = lat, lon = lon, lang = lang, callback = callback)
    }

}