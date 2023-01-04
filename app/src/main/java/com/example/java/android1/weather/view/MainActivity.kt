package com.example.java.android1.weather.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.details.DetailsScreen
import com.example.java.android1.weather.view.main.HomeScreen
import com.example.java.android1.weather.viewmodel.MainViewModel

const val WEATHER_DATA_KEY = "Weather.Data.Key"

class MainActivity : ComponentActivity() {

    private val homeViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(homeViewModel)
        }
    }
}

@Composable
fun Navigation(homeViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(viewModel = homeViewModel, navController = navController)
        }
        composable(
            route = Screen.DetailWeatherScreen.route,
        ) {
            val weather = it.arguments?.getParcelable<WeatherDTO>(WEATHER_DATA_KEY)
            weather?.let { it1 -> DetailsScreen(weather) }
        }
    }
}