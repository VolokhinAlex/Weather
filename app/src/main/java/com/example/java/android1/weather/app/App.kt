package com.example.java.android1.weather.app

import android.app.Application
import androidx.room.Room
import com.example.java.android1.weather.room.WeatherDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        var appInstance: App? = null
        private const val DB_NAME = "Weather.db"

        val weather_dao by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                WeatherDataBase::class.java,
                DB_NAME
            )
                .build()
                .weatherDao()
        }

        val city_dao by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                WeatherDataBase::class.java,
                DB_NAME
            )
                .build()
                .cityDao()
        }

    }

}