package com.example.java.android1.weather.room

import com.example.java.android1.weather.model.*

fun convertWeatherEntityToWeatherDto(weatherEntity: List<WeatherEntity>): List<WeatherDTO> {
    return weatherEntity.map {
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                it.temperature, null, it.condition, null,
                null, null, null, it.icon
            ),
            geo_object = GeoDTO(
                CountryDTO(it.country ?: ""),
                LocalityDTO(it.city)
            ),
            forecasts = null,
            info = Info(CityInfo(1), it.lat ?: 0.0, it.lon ?: 0.0)
        )
    }
}

fun convertWeatherDtoToWeatherEntity(weatherDTO: WeatherDTO): WeatherEntity {
    return WeatherEntity(
        weatherDTO.geo_object?.locality?.name.toString(),
        weatherDTO.geo_object?.country?.name.toString(),
        weatherDTO.info?.lat,
        weatherDTO.info?.lon,
        weatherDTO.fact?.temp,
        weatherDTO.fact?.condition,
        weatherDTO.fact?.icon
    )
}
