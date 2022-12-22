package com.example.java.android1.weather.repository

import com.example.java.android1.weather.BuildConfig
import com.example.java.android1.weather.model.WeatherDTO
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(WeatherInterceptor()))
        .build().create(WeatherApi::class.java)


    fun getWeatherDetail(lat: Double, lon: Double, lang: String, callback: Callback<WeatherDTO>) {
        val request = weatherApi.getWeatherDetail(
            token = BuildConfig.WEATHER_API_KEY,
            lat = lat,
            lon = lon,
            lang = lang
        )
        request.enqueue(callback)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
        client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return client.build()
    }


    class WeatherInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }

}