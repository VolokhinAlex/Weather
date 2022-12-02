package com.example.java.android1.weather.model

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
    val sunset: String = ""
)

fun getDefaultCity() = City("Новосибирск", 5.000, 5.000)

fun getDefaultWeather() = Weather(
    city = getDefaultCity(),
    temperature = 26,
    feelsLike = 30,
    condition = "cloudy",
    windSpeed = 0.9,
    humidity = 81,
    daytime = "d",
    precipitation = 0,
    date = "2022-12-02",
    sunrise = "04:38",
    sunset = "20:31"
)