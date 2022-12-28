package com.example.java.android1.weather.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "weather_table", foreignKeys = [ForeignKey(
//        entity = CityEntity::class,
//        parentColumns = arrayOf("city"),
//        childColumns = arrayOf("city")
//    )]
//)
//data class WeatherEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long,
//    val city: String,
//    val temperature: Int?,
//    val condition: String?,
//    val icon: String?,
//)

@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey val city: String,
    val country: String?,
    val lat: Double,
    val lon: Double
)
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