package com.example.java.android1.weather.view.main

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.Screen
import com.example.java.android1.weather.view.WEATHER_DATA_KEY
import com.example.java.android1.weather.view.navigate
import com.example.java.android1.weather.viewmodel.AppState
import com.example.java.android1.weather.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val homeViewModel by viewModel.liveData.observeAsState()
    val cityState = remember {
        mutableStateOf("World")
    }
    homeViewModel?.let {
        RenderDataComposable(it, navController, viewModel, cityState)
    }
}

@Composable
fun RenderDataComposable(
    appState: AppState,
    navController: NavController,
    viewModel: MainViewModel,
    cityState: MutableState<String>
) {
    when (appState) {
        is AppState.Error -> NotFoundData()
        is AppState.Success -> {
            val weatherData = appState.weatherData
            CitiesListView(weatherData, navController, viewModel, cityState)
        }
        AppState.Loading -> {
            Loading()
        }
    }
}

@Composable
fun NotFoundData() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Not found Data",
            modifier = Modifier.align(Alignment.Center), fontSize = 20.sp
        )
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Green
        )
    }
}

@Composable
private fun CitiesListView(
    weather: List<WeatherDTO>,
    navController: NavController,
    viewModel: MainViewModel,
    cityState: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_contacts_24),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.ContactsScreen.route)
                    }
                )
            }
            itemsIndexed(weather) { _, item ->
                Surface(modifier = Modifier.clickable {
                    val bundle = Bundle()
                    bundle.putParcelable(WEATHER_DATA_KEY, item)
                    navController.navigate(Screen.DetailWeatherScreen.route, bundle)
                }) {
                    CityCardView(item)
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (cityState.value == "World") {
                    viewModel.getWeatherFromLocalSourceWorld()
                    cityState.value = "Russian"
                } else {
                    viewModel.getWeatherFromLocalSourceRus()
                    cityState.value = "World"
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 5.dp, bottom = 5.dp),
            elevation = FloatingActionButtonDefaults.elevation(5.dp)
        ) {
            Text(text = cityState.value)
        }
    }
}