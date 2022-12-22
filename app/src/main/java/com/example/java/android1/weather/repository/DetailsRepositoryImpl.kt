package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.WeatherDTO
import retrofit2.Callback

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    override fun getWeatherDetailFromServer(
        lat: Double,
        lon: Double,
        lang: String,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetail(lat = lat, lon = lon, lang = lang, callback = callback)
    }

}