package com.example.java.android1.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val icon: String = "",
    val condition: String = "",
    val windSpeed: Double = 0.toDouble(),
    val humidity: Int = 0,
    val daytime: String = "",
    val precipitation: Int = 0,
    val date: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val pressure: Int = 0
) : Parcelable

fun getDefaultCity() = City("Новосибирск", "Россия", 5.000, 5.000)