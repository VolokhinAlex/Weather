package com.example.java.android1.weather.app

import com.example.java.android1.weather.model.WeatherDTO

/**
 * The class for determining the response status from the remote or local repository
 * The class contains 3 states.
 * 1. [Success]   ->  If the local or remote repository return data successfully then Success State is returned with list of weather
 * 2. [Error]     ->  If server can't get data from local or remote repository then the Error State is returned with the message
 * 3. [Loading]  ->  Waiting states for receiving a response from the server
 */

sealed class WeatherAppState {
    data class Success(val weatherData: List<WeatherDTO>) : WeatherAppState()
    data class Error(val error: Throwable) : WeatherAppState()
    object Loading : WeatherAppState()
}