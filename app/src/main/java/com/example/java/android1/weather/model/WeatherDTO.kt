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

/**
 * Current weather in a particular city
 * @param temp - current temperature in the city
 * @param feels_like - feels temperature in the city
 * @param condition - current condition in the city
 * @param wind_speed - current wind_speed in the city
 * @param pressure_mm - current pressure_mm in the city
 * @param humidity - current humidity in the city
 * @param daytime - Current time of day
 * @param icon - Icon with the current condition weather
 */

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

/**
 * Weather for several days
 * @param date - The forecast date in the format YYYY-MM-DD.
 * @param date_ts -The forecast date in the format UNIX.
 * @param sunrise - Sunrise Time
 * @param sunset - Sunset Time
 * @param hours - Weather by the hour
 * @param parts - Forecasts by time of day.
 */

@Parcelize
data class ForecastsDTO(
    val date: String?,
    val date_ts: Long?,
    val sunrise: String?,
    val sunset: String?,
    val hours: List<HoursDTO>?,
    val parts: Parts?
) : Parcelable

/**
 * Object for storing the current city and country
 */

@Parcelize
data class GeoDTO(
    val country: CountryDTO?,
    val locality: LocalityDTO?
) : Parcelable

/**
 * Object for storing the current country
 */

@Parcelize
data class CountryDTO(val name: String) : Parcelable

/**
 * Object for storing the current city
 */

@Parcelize
data class LocalityDTO(val name: String) : Parcelable

/**
 * Object for storing coordinates and hourly offset
 * @param tzinfo - Object for storing hourly offset
 * @param lat - The city of latitude
 * @param lon - The city of longitude
 */

@Parcelize
data class Info(val tzinfo: CityInfo, val lat: Double, val lon: Double) : Parcelable

/**
 * Object for storing hourly offset
 * @param offset - hourly offset
 */

@Parcelize
data class CityInfo(val offset: Int?) : Parcelable

/**
 * An object for storing hourly weather for 7 days
 * @param hour - hour of weather measurement
 * @param hour_ts - hour of weather measurement in the UNIX format
 * @param temp - the temperature of a particular hour
 * @param feels_like -  the feels temperature of a particular hour
 * @param feels_like -  the feels temperature of a particular hour
 * @param icon -  Icon with the current condition weather
 * @param condition -  the condition of a particular hour
 */

@Parcelize
data class HoursDTO(
    val hour: String,
    val hour_ts: Long?,
    val temp: Int?,
    val feels_like: Int?,
    val icon: String?,
    val condition: String?,
) : Parcelable

/**
 * Forecasts by time of day.
 * @param day - Weather of the day
 * @param night - Weather of the night
 */

@Parcelize
data class Parts(
    val day: DayOfWeather?,
    val night: NightOfWeather?
) : Parcelable

/**
 * Weather of the day
 * @param temp_min - Minimum weather of the day
 * @param temp_avg - Average weather of the day
 * @param temp_max - Maximum weather of the day
 * @param icon - Icon with the condition weather
 */

@Parcelize
data class DayOfWeather(
    val temp_min: Int?,
    val temp_avg: Int?,
    val temp_max: Int?,
    val icon: String?
): Parcelable

/**
 * Weather of the night
 * @param temp_min - Minimum weather of the day
 * @param temp_avg - Average weather of the day
 * @param temp_max - Maximum weather of the day
 * @param icon - Icon with the condition weather
 */

@Parcelize
data class NightOfWeather(
    val temp_min: Int?,
    val temp_avg: Int?,
    val temp_max: Int?,
    val icon: String?
): Parcelable