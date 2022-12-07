package com.example.java.android1.weather.model

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return listOf(
            Weather(
                City("Москва", "Россия", 55.755826, 37.617299900000035),
                1,
                2,
                "",
                "cloudy",
                0.9,
                67,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Санкт-Петербург", "Россия", 59.9342802, 30.335098600000038),
                3,
                3,
                "",
                "cloudy",
                0.9,
                97,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Новосибирск", "Россия", 55.00835259999999, 82.93573270000002),
                5,
                6,
                "",
                "cloudy",
                0.9,
                87,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Екатеринбург", "Россия", 56.83892609999999, 60.60570250000001),
                7,
                8,
                "",
                "cloudy",
                0.9,
                68,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Нижний Новгород", "Россия", 56.2965039, 43.936059),
                9,
                10,
                "",
                "cloudy",
                0.9,
                46,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Казань", "Россия", 55.8304307, 49.06608060000008),
                11,
                12,
                "",
                "cloudy",
                0.9,
                90,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Челябинск", "Россия", 55.1644419, 61.4368432),
                13,
                14,
                "",
                "cloudy",
                0.9,
                100,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Омск", "Россия", 54.9884804, 73.32423610000001),
                15,
                16,
                "",
                "cloudy",
                0.9,
                87,
                "d"
            ),
            Weather(
                City("Ростов-на-Дону", "Россия", 47.2357137, 39.701505),
                17,
                18,
                "",
                "cloudy",
                0.9,
                88,
                "d"
            ),
            Weather(
                City("Уфа", "Россия", 54.7387621, 55.972055400000045),
                19,
                20,
                "",
                "cloudy",
                0.9,
                66,
                "d"
            )
        )
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return listOf(
            Weather(
                City("Лондон", "Великобритания", 51.5085300, -0.1257400),
                1,
                2,
                "",
                "cloudy",
                0.9,
                32,
                "d",
                sunset = "6:47 PM",
                sunrise = "6:05 AM"
            ),
            Weather(
                City("Токио", "Япония", 35.6895000, 139.6917100),
                3,
                4,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Париж", "Франции", 48.8534100, 2.3488000),
                5,
                6,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Берлин", "Германии", 52.52000659999999, 13.404953999999975),
                7,
                8,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Рим", "Италия", 41.9027835, 12.496365500000024),
                9,
                10,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Минск", "Беларуси ", 53.90453979999999, 27.561524400000053),
                11,
                12,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Стамбул", "Турция", 41.0082376, 28.97835889999999),
                13,
                14,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(
                City("Вашингтон", "США", 38.9071923, -77.03687070000001),
                15,
                16,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            ),
            Weather(City("Киев", "Украина", 50.4501, 30.523400000000038), 17, 18),
            Weather(
                City("Пекин", "Китай", 39.90419989999999, 116.40739630000007),
                19,
                20,
                "",
                "cloudy",
                0.9,
                67,
                "d"
            )
        )
    }
}