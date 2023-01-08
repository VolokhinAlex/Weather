package com.example.java.android1.weather.room

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table")
    fun all(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WeatherEntity)

    @Update
    fun update(entity: WeatherEntity)

}