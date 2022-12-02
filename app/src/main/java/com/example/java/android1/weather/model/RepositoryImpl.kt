package com.example.java.android1.weather.model

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather(getDefaultCity(), -15, -20)
    }

}