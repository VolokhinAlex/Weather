package com.example.java.android1.weather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey
    val city: String,
    val country: String?,
    val lat: Double?,
    val lon: Double?,
    val temperature: Int?,
    val condition: String?,
    val icon: String?,
)