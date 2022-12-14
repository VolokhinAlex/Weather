package com.example.java.android1.weather.model

class WeatherDTO(
    val now: Long,
    val fact: FactDTO?,
    val geo_object: GeoDTO?,
    val forecasts: List<ForecastsDTO>?
)

data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val wind_speed: Double?,
    val pressure_mm: Double?,
    val humidity: Int?,
    val daytime: String?,
    val icon: String?
)

data class ForecastsDTO(
    val date_ts: Long?,
    val sunrise: String?,
    val sunset: String?,
    val hours: List<HoursDTO>?
)

data class GeoDTO(
    val country: CountryDTO?,
    val locality: LocalityDTO?
)

data class CountryDTO(val name: String)
data class LocalityDTO(val name: String)

data class HoursDTO(
    val hour: String,
    val hour_ts: Long?,
    val temp: Int?,
    val feels_like: Int?,
    val icon: String?,
    val condition: String?,
)
