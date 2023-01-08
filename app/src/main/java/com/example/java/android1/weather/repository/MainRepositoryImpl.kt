package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.*
import retrofit2.Callback

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    override fun getWeatherFromRemoteSource(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getDetailsWeather(lat, lon, lang, callback)
    }

}