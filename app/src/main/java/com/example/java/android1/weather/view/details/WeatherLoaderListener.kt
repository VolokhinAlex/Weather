package com.example.java.android1.weather.view.details

import com.example.java.android1.weather.model.WeatherDTO

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onFailed(error: Throwable)
}