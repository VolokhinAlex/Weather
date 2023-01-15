package com.example.java.android1.weather.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.java.android1.weather.app.App.Companion.weather_dao
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.repository.DetailsRepositoryImpl
import com.example.java.android1.weather.repository.MainRepositoryImpl
import com.example.java.android1.weather.repository.RemoteDataSource
import com.example.java.android1.weather.repository.WeatherLocalRepositoryImpl
import com.example.java.android1.weather.view.details.DetailsScreen
import com.example.java.android1.weather.view.main.HomeScreen
import com.example.java.android1.weather.viewmodel.DetailsViewModel
import com.example.java.android1.weather.viewmodel.DetailsViewModelFactory
import com.example.java.android1.weather.viewmodel.MainViewModel
import com.example.java.android1.weather.viewmodel.MainViewModelFactory

const val WEATHER_DATA_KEY = "Weather.Data.Key"

class MainActivity : ComponentActivity() {
    private val remoteDataSource = RemoteDataSource()
    private val homeViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            MainRepositoryImpl(remoteDataSource),
            WeatherLocalRepositoryImpl(weather_dao)
        )
    }
    private val detailsViewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(
            DetailsRepositoryImpl(remoteDataSource),
            WeatherLocalRepositoryImpl(weather_dao)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(homeViewModel, detailsViewModel)
        }
    }
}

@Composable
fun Navigation(homeViewModel: MainViewModel, detailsViewModel: DetailsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(mainViewModel = homeViewModel, navController = navController)
        }
        composable(
            route = Screen.DetailWeatherScreen.route,
        ) {
            val weatherData = it.arguments?.getParcelable<WeatherDTO>(WEATHER_DATA_KEY)
            weatherData?.let { weather ->
                DetailsScreen(
                    weatherDTO = weather,
                    detailsViewModel = detailsViewModel,
                    navController = navController
                )
            }
        }
    }
}