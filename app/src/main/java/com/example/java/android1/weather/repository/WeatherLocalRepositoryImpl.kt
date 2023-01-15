package com.example.java.android1.weather.repository

import androidx.annotation.WorkerThread
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.room.WeatherDao
import com.example.java.android1.weather.room.convertWeatherDtoToWeatherEntity
import com.example.java.android1.weather.room.convertWeatherEntityToWeatherDto

/**
 * The implementation WeatherLocalRepository for interacting with local database
 */

class WeatherLocalRepositoryImpl(
    private val localWeatherDao: WeatherDao
) : WeatherLocalRepository {

    override fun getAllWeatherList(): List<WeatherDTO> =
        convertWeatherEntityToWeatherDto(localWeatherDao.all())

    @WorkerThread
    override suspend fun insertWeather(weatherDTO: WeatherDTO) =
        localWeatherDao.insert(convertWeatherDtoToWeatherEntity(weatherDTO))


    @WorkerThread
    override suspend fun updateWeather(weatherDTO: WeatherDTO) =
        localWeatherDao.update(convertWeatherDtoToWeatherEntity(weatherDTO))

}