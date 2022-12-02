package com.example.java.android1.weather.model

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0)

fun getDefaultCity() = City("Новосибирск", 5.000, 5.000)