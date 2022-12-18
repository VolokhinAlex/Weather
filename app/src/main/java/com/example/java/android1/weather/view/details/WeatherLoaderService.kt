package com.example.java.android1.weather.view.details

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.java.android1.weather.BuildConfig
import com.example.java.android1.weather.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val WEATHER_LAT = "lat"
const val WEATHER_LON = "lon"
const val WEATHER_DATA = "Weather data"
const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_URL_MALFORMED_EXTRA_MESSAGE = "URL MALFORMED MESSAGE"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"

class WeatherLoaderBroadcastReceiver(name: String = "WeatherLoaderBroadcastReceiver") :
    IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    override fun onHandleIntent(intent: Intent?) {
       if (intent == null) {
           onEmptyIntent()
       } else {
           val lat = intent.getDoubleExtra(WEATHER_LAT, 0.0)
           val lon = intent.getDoubleExtra(WEATHER_LON, 0.0)
           if (lat == 0.0 && lon == 0.0) {
               onEmptyIntent()
           } else {
               loadData(lat.toString(), lon.toString())
           }
       }
    }

    private fun loadData(lat: String, lon: String) {

        try {
            val url = URL("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}&extra=true&lang=ru_RU")
            var urlConnection: HttpsURLConnection? = null
            try {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    BuildConfig.WEATHER_API_KEY
                )
                urlConnection.readTimeout = 10000
                urlConnection.requestMethod = "GET"

                val result = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(result), WeatherDTO::class.java)
                onResponseData(weatherDTO)
            } catch (error: Exception) {
                onErrorParseData(error)
            } finally {
                urlConnection?.disconnect()
            }
        } catch (error: MalformedURLException) {
            onMalformedUrlError(error)
        }

    }

    private fun getLines(reader: BufferedReader): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reader.lines().collect(Collectors.joining("\n"))
        } else {
            ""
        }
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onResponseData(weatherDTO: WeatherDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(WEATHER_DATA, weatherDTO)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorParseData(error: Exception) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error.toString())
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedUrlError(error: MalformedURLException) {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        broadcastIntent.putExtra(DETAILS_URL_MALFORMED_EXTRA_MESSAGE, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

}