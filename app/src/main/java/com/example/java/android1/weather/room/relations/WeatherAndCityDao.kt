package com.example.java.android1.weather.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.java.android1.weather.room.CityEntity
import com.example.java.android1.weather.room.WeatherEntity

data class WeatherAndCityDao(
    @Embedded val weather: WeatherEntity,
    @Relation(
        parentColumn = "city",
        entityColumn = "city"
    )
    val city: CityEntity
)
