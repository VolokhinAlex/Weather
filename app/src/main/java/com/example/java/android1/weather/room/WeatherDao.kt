package com.example.java.android1.weather.room

import androidx.room.*
import com.example.java.android1.weather.room.relations.WeatherAndCityDao

@Dao
interface WeatherDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWeather(weatherEntity: WeatherEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCity(cityEntity: CityEntity)
//
//    @Query("SELECT * FROM weather_table WHERE city = :cityName")
//    fun getWeatherByCityName(cityName: String): List<WeatherAndCityDao>

    @Query("SELECT * FROM weather_table")
    fun all(): List<WeatherEntity>

    @Query("SELECT * FROM weather_table WHERE country LIKE \"Russia\"")
    fun getCityRusWeather() : List<WeatherEntity>

    @Query("SELECT * FROM weather_table WHERE country != \"Russia\"")
    fun getCityWorldWeather() : List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WeatherEntity)

    @Update
    fun update(entity: WeatherEntity)

}