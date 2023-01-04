package com.example.java.android1.weather.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [WeatherEntity::class, CityEntity::class],
    version = 1,
    exportSchema = false
)

abstract class WeatherDataBase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao

}