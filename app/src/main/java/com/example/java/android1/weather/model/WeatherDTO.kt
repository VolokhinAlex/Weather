package com.example.java.android1.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class WeatherDTO(
    val now: Long,
    val fact: FactDTO?,
    val geo_object: GeoDTO?,
    val forecasts: List<ForecastsDTO>?,
    val info: Info?
) : Parcelable

@Parcelize
data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val wind_speed: Double?,
    val pressure_mm: Double?,
    val humidity: Int?,
    val daytime: String?,
    val icon: String?
) : Parcelable

@Parcelize
data class ForecastsDTO(
    val date: String?,
    val date_ts: Long?,
    val sunrise: String?,
    val sunset: String?,
    val hours: List<HoursDTO>?,
    val parts: Parts?
) : Parcelable

@Parcelize
data class GeoDTO(
    val country: CountryDTO?,
    val locality: LocalityDTO?
) : Parcelable

@Parcelize
data class CountryDTO(val name: String) : Parcelable

@Parcelize
data class LocalityDTO(val name: String) : Parcelable

@Parcelize
data class Info(val tzinfo: CityInfo, val lat: Double, val lon: Double) : Parcelable

@Parcelize
data class CityInfo(val offset: Int?) : Parcelable

@Parcelize
data class HoursDTO(
    val hour: String,
    val hour_ts: Long?,
    val temp: Int?,
    val feels_like: Int?,
    val icon: String?,
    val condition: String?,
) : Parcelable

@Parcelize
data class Parts(
    val day: DayOfWeather?,
    val night: NightOfWeather?
) : Parcelable

@Parcelize
data class DayOfWeather(
    val temp_min: Int?,
    val temp_avg: Int?,
    val temp_max: Int?,
    val icon: String?
): Parcelable

@Parcelize
data class NightOfWeather(
    val temp_min: Int?,
    val temp_avg: Int?,
    val temp_max: Int?,
    val icon: String?
): Parcelable