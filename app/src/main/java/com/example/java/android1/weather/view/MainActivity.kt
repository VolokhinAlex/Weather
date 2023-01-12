package com.example.java.android1.weather.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.details.DetailsWeatherContent
import com.example.java.android1.weather.view.details.DetailsWeatherFragment
import com.example.java.android1.weather.view.details.WeatherLoader
import com.example.java.android1.weather.view.details.WeatherLoaderListener
import com.example.java.android1.weather.view.main.HomeScreen
import com.example.java.android1.weather.viewmodel.MainViewModel
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //val navController = Navigation.findNavController(this, R.id.container)
        setContent {
            Navigation(viewModel)
        }
    }
}

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.DetailWeatherScreen.route,
        ) {
            val weather = it.arguments?.getParcelable<WeatherDTO>(DetailsWeatherFragment.ARG_WEATHER_DATA_KEY)
//            var data: WeatherDTO? = null
//            weather?.city?.let {
//                val loader = WeatherLoader(object : WeatherLoaderListener {
//                    override fun onLoaded(weatherDTO: WeatherDTO) {
//                        data = weatherDTO
//                        Log.d("", data.toString())
//                    }
//
//                    override fun onFailed(error: Throwable) {
//                    }
//                }, it.lat, it.lon, Locale.getDefault().toString())
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    loader.loadWeather()
//                }
//            }
            weather?.let { it1 -> DetailsWeatherContent(weatherDTO = it1) }
        }
    }
}