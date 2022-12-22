package com.example.java.android1.weather.repository

import com.example.java.android1.weather.model.*

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    override fun getWeatherFromLocalStorageRus(): List<WeatherDTO> = listOf(
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                1, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Москва")),
            forecasts = null,
            info = Info(CityInfo(1), 55.755826, 37.617299900000035)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                3, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Санкт-Петербург")),
            forecasts = null,
            info = Info(CityInfo(1), 59.9342802, 30.335098600000038)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                5, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Новосибирск")),
            forecasts = null,
            info = Info(CityInfo(1), 55.00835259999999, 82.93573270000002)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                7, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Екатеринбург")),
            forecasts = null,
            info = Info(CityInfo(1), 56.83892609999999, 60.60570250000001)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                9, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Нижний Новгород")),
            forecasts = null,
            info = Info(CityInfo(1), 56.2965039, 43.936059)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                11, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Казань")),
            forecasts = null,
            info = Info(CityInfo(1), 55.8304307, 49.06608060000008)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                13, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Челябинск")),
            forecasts = null,
            info = Info(CityInfo(1), 55.1644419, 61.4368432)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                15, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Омск")),
            forecasts = null,
            info = Info(CityInfo(1), 54.9884804, 73.32423610000001)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                17, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Ростов-на-Дону")),
            forecasts = null,
            info = Info(CityInfo(1), 47.2357137, 39.701505)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                19, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Россия"), LocalityDTO("Уфа")),
            forecasts = null,
            info = Info(CityInfo(1), 54.7387621, 55.972055400000045)
        ))

    override fun getWeatherFromLocalStorageWorld(): List<WeatherDTO> = listOf(
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                1, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Великобритания"), LocalityDTO("Лондон")),
            forecasts = null,
            info = Info(CityInfo(1), 51.5085300, -0.1257400)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                3, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Япония"), LocalityDTO("Токио")),
            forecasts = null,
            info = Info(CityInfo(1), 35.6895000, 139.6917100)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                5, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Франции"), LocalityDTO("Париж")),
            forecasts = null,
            info = Info(CityInfo(1), 48.8534100, 2.3488000)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                7, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Германии"), LocalityDTO("Берлин")),
            forecasts = null,
            info = Info(CityInfo(1), 52.52000659999999, 13.404953999999975)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                9, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Италия"), LocalityDTO("Рим")),
            forecasts = null,
            info = Info(CityInfo(1), 41.9027835, 12.496365500000024)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                11, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Беларуси"), LocalityDTO("Минск")),
            forecasts = null,
            info = Info(CityInfo(1), 53.90453979999999, 27.561524400000053)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                13, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Турция"), LocalityDTO("Стамбул")),
            forecasts = null,
            info = Info(CityInfo(1), 41.0082376, 28.97835889999999)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                15, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("США"), LocalityDTO("Вашингтон")),
            forecasts = null,
            info = Info(CityInfo(1), 38.9071923, -77.03687070000001)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                17, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Украина"), LocalityDTO("Киев")),
            forecasts = null,
            info = Info(CityInfo(1), 50.4501, 30.523400000000038)
        ),
        WeatherDTO(
            now = 1L,
            fact = FactDTO(
                19, null, "cloudy", null,
                null, null, null, null
            ),
            geo_object = GeoDTO(CountryDTO("Китай"), LocalityDTO("Пекин")),
            forecasts = null,
            info = Info(CityInfo(1), 39.90419989999999, 116.40739630000007)
        ))

}