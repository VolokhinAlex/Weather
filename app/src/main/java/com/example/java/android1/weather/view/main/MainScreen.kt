package com.example.java.android1.weather.view.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.java.android1.weather.app.AppState
import com.example.java.android1.weather.app.LocationState
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.Screen
import com.example.java.android1.weather.view.WEATHER_DATA_KEY
import com.example.java.android1.weather.view.navigate
import com.example.java.android1.weather.view.search.SearchBar
import com.example.java.android1.weather.view.search.SearchDisplay
import com.example.java.android1.weather.view.search.SearchState
import com.example.java.android1.weather.view.search.rememberSearchState
import com.example.java.android1.weather.view.widgets.CityCardView
import com.example.java.android1.weather.view.widgets.ErrorMessage
import com.example.java.android1.weather.view.widgets.Loading
import com.example.java.android1.weather.view.widgets.MessageDialog
import com.example.java.android1.weather.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import java.util.*

/**
 * The Home screen is the main method, which includes all the methods for the operation of this screen
 */

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val homeViewModel by viewModel.liveData.observeAsState()
    val locationLiveData by viewModel.locationState.observeAsState()
    homeViewModel?.let {
        RenderWeatherData(it, navController, viewModel)
    }
    locationLiveData?.let {
        RenderLocationData(it, viewModel)
    }
    LaunchedEffect(Unit) {
        viewModel.getWeatherFromLocalDataBase()
    }
}

/**
 * The method checks the permission for the location, if the user has permission, the [getCurrentLocation] method is called
 * If the user does not have permission, he will be asked for permission again
 */

private fun checkPermission(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    viewModel: MainViewModel
) {
    when {
        permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } -> {
            getCurrentLocation(context, viewModel)
        }
        else -> {
            launcher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }
}

/**
 * The method requests user's current location if he has given permission to determine the location.
 * And returns two states ->
 * 1. Success - return the lat, lon to Location State
 * 2. NotEnabledGPS - return the message to Location State and call the Message Dialog
 */

fun getCurrentLocation(context: Context, viewModel: MainViewModel) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val location = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            location?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    60000L,
                    100.0F
                ) {
                    viewModel.getLocationSuccess(it.latitude, it.longitude)
                }
            }
        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                viewModel.getLocationNotEnabledGPS("Your GPS is not enabled")
            } else {
                viewModel.getLocationSuccess(location.latitude, location.longitude)
            }
        }
    }
}

/**
 *  The method is processing location state which came from the method [getCurrentLocation]
 */

@Composable
fun RenderLocationData(locationState: LocationState, viewModel: MainViewModel) {
    when (locationState) {
        is LocationState.Success -> {
            viewModel.getWeatherCityFromRemoteSource(
                locationState.lat,
                locationState.lon,
                Locale.getDefault().language
            )
        }
        is LocationState.NotEnabledGPS -> {
            MessageDialog(
                modifier = Modifier.padding(10.dp),
                title = { Text(text = locationState.message) },
                text = { Text(text = "You need to enable the GPS Tracker, else you can't get weather near your location") },
                confirmButton = {
                    Text(text = "OK")
                })
        }
    }
}

/**
 * The method handles weather conditions [AppState] that come from live data.
 * There are three states in total.
 * 1. Success -> The user has successfully received the city weather data from a local or remote repository.
 * 2. Loading -> Data in the receiving state. As soon as the server responds, the Success or Error state will return
 * 3. Error -> If the data that the user requested was not found or the connection with the server was severed
 */

@Composable
fun RenderWeatherData(
    appState: AppState,
    navController: NavController,
    viewModel: MainViewModel,
) {
    when (appState) {
        is AppState.Error -> appState.error.message?.let { ErrorMessage(it) }
        is AppState.Success -> {
            val weatherData = appState.weatherData
            if (weatherData.isEmpty()) {
                ErrorMessage(text = "No cities have been added yet")
            }
            val state: SearchState = rememberSearchState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                SearchField(viewModel = viewModel, state = state)
                var weatherList = weatherData.toMutableList()
                when (state.searchDisplay) {
                    SearchDisplay.InitialResults -> {
                        weatherList = weatherData.toMutableList()
                    }
                    SearchDisplay.NoResults -> {
                        weatherList.clear()
                    }
                    else -> {}
                }
                CitiesListView(weatherList, navController)
            }
        }
        AppState.Loading -> Loading()
    }
}

/**
 * The method is called if the user has received the weather data successfully. see the [RenderWeatherData] rendering method (Success).
 * The method creates a list of cities with weather based on data that came from a local or remote repository.
 * @param weather - List of cities with weather.
 * @param navController - Navigation Controller for interaction between Home Screen and Details Screen
 */

@Composable
private fun CitiesListView(
    weather: List<WeatherDTO>,
    navController: NavController,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        itemsIndexed(weather) { _, item ->
            Surface(modifier = Modifier
                .clickable {
                    val bundle = Bundle()
                    bundle.putParcelable(WEATHER_DATA_KEY, item)
                    navController.navigate(Screen.DetailWeatherScreen.route, bundle)
                }) {
                CityCardView(item)
            }
        }
    }
}

/**
 * The method adds a search text field to the main screen. When the user enters a city, a search occurs,
 * if the city is successfully found, the city will appear on the screen with the current weather.
 * In the method, a button is added to request the weather by the user's location,
 * if permission is available, the city with the current weather will return.
 * Otherwise, permission to use geolocation will be requested again
 * View the method [getCurrentLocation] and [checkPermission]
 * @param state - State of Search [SearchDisplay]
 * @param geocoder - Needed to get the coordinates of the city by name
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    state: SearchState,
    context: Context = LocalContext.current,
    geocoder: MutableState<Geocoder> = remember {
        mutableStateOf(Geocoder(context))
    }
) {
    val requestPermissions =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value) {
                    getCurrentLocation(context, viewModel)
                }
            }
        }

    SearchBar(
        query = state.query,
        onQueryChange = { state.query = it },
        onSearchFocusChange = { state.focused = it },
        onClearQuery = { state.query = TextFieldValue("") },
        onBack = {
            state.query = TextFieldValue("")
            viewModel.getWeatherFromLocalDataBase()
        },
        searching = state.searching,
        focused = state.focused,
        modifier = modifier,
        searchHint = "Enter a city to find",
        location = {
            checkPermission(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissions,
                viewModel
            )
        }
    )

    LaunchedEffect(state.query.text) {
        state.searching = true
        delay(500)
        if (state.query.text.length > 2) {
            viewModel.getCityCoordination(query = state.query.text, geocoder = geocoder.value)
        }
        state.searching = false
    }
}