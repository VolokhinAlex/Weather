package com.example.java.android1.weather.model

interface Repository {

    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorage(): Weather

}