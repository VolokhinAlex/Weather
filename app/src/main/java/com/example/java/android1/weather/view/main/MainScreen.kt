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
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.java.android1.weather.R
import com.example.java.android1.weather.app.LocationState
import com.example.java.android1.weather.app.WeatherAppState
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.LanguageQuery
import com.example.java.android1.weather.view.Screen
import com.example.java.android1.weather.view.WEATHER_DATA_KEY
import com.example.java.android1.weather.view.navigate
import com.example.java.android1.weather.view.search.SearchBar
import com.example.java.android1.weather.view.search.SearchDisplay
import com.example.java.android1.weather.view.search.SearchState
import com.example.java.android1.weather.view.search.rememberSearchState
import com.example.java.android1.weather.view.widgets.CityCardView
import com.example.java.android1.weather.view.widgets.ErrorMessage
import com.example.java.android1.weather.view.widgets.LoadingProgressBar
import com.example.java.android1.weather.view.widgets.MessageDialog
import com.example.java.android1.weather.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import java.util.*

/**
 * The Home screen is the main method, which includes all the methods for the operation of this screen
 * @param mainViewModel - Main View Model [MainViewModel]
 * @param navController - Controller for screen navigation
 */

@Composable
fun HomeScreen(mainViewModel: MainViewModel, navController: NavController) {
    val searchState: SearchState = rememberSearchState()
    mainViewModel.weatherData.observeAsState().value?.let {
        RenderWeatherData(it, navController, mainViewModel, searchState)
    }
    mainViewModel.weatherLocationData.observeAsState().value?.let {
        RenderLocationData(it, mainViewModel)
    }
    LaunchedEffect(true) {
        mainViewModel.getWeatherFromLocalDataBase()
    }
}

/**
 * The method checks the permission for the location, if the user has permission, the [getCurrentLocation] method is called
 * If the user does not have permission, he will be asked for permission again
 * @param context - App context
 * @param permissions - List of permissions
 * @param launcher - To request location permission
 * @param mainViewModel - Main View Model
 */

private fun checkLocationPermission(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    mainViewModel: MainViewModel
) {
    when {
        permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } -> {
            getCurrentLocation(context, mainViewModel)
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
 * @param context - App context
 * @param mainViewModel - Main View Mod
 */

private fun getCurrentLocation(context: Context, mainViewModel: MainViewModel) {
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
                    mainViewModel.getWeatherByLocation(it.latitude, it.longitude)
                }
            }
        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                mainViewModel.getWeatherByLocationError("Your GPS is not enabled")
            } else {
                mainViewModel.getWeatherByLocation(location.latitude, location.longitude)
            }
        }
    }
}

/**
 *  The method is processing location state which came from the method [getCurrentLocation]
 * @param locationState - The status that comes from the remote server
 * @param mainViewModel - Main View Model
 */

@Composable
private fun RenderLocationData(locationState: LocationState, mainViewModel: MainViewModel) {
    when (locationState) {
        is LocationState.Success -> {
            LaunchedEffect(key1 = true) {
                mainViewModel.getWeatherCityFromRemoteServer(
                    locationState.lat,
                    locationState.lon,
                    LanguageQuery.EN.languageQuery
                )
            }
        }
        is LocationState.NotEnabledGPS -> {
            val dialogState = remember {
                mutableStateOf(true)
            }
            MessageDialog(
                modifier = Modifier.padding(10.dp),
                title = { Text(text = locationState.message) },
                text = { Text(text = stringResource(id = R.string.not_enabled_gps)) },
                confirmButton = {
                    Text(
                        text = stringResource(id = R.string.confirm_btn),
                        modifier = Modifier.clickable { dialogState.value = false })
                },
                dialogState = dialogState
            )
        }
    }
}

/**
 * The method handles weather conditions [WeatherAppState] that come from live data.
 * There are three states in total.
 * 1. Success -> The user has successfully received the city weather data from a local or remote repository.
 * 2. Loading -> Data in the receiving state. As soon as the server responds, the Success or Error state will return
 * 3. Error -> If the data that the user requested was not found or the connection with the server was severed
 * @param weatherAppState - The status that comes from the remote server
 * @param navController - Controller for screen navigation
 * @param mainViewModel - Main View Model
 * @param searchState - State of Search [SearchDisplay]
 */

@Composable
private fun RenderWeatherData(
    weatherAppState: WeatherAppState,
    navController: NavController,
    mainViewModel: MainViewModel,
    searchState: SearchState
) {
    when (weatherAppState) {
        is WeatherAppState.Error -> weatherAppState.error.localizedMessage?.let { ErrorMessage(text = it) }
        WeatherAppState.Loading -> LoadingProgressBar()
        is WeatherAppState.Success -> {
            val weatherListCities = weatherAppState.weatherData
            if (weatherListCities.isEmpty()) {
                ErrorMessage(text = stringResource(id = R.string.not_added_cities))
            }
            searchState.searchResults = weatherListCities
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                SearchField(mainViewModel = mainViewModel, searchState = searchState)
                RenderSearchData(
                    searchState = searchState,
                    weatherListCities = weatherListCities.toMutableList(),
                    navController = navController
                )
            }
        }
    }
}

/**
 * A method for processing data that came from the user's search
 * @param searchState - State of Search [SearchDisplay]
 * @param weatherListCities - List of cities with weather.
 * @param navController - Controller for screen navigation
 */

@Composable
private fun RenderSearchData(
    searchState: SearchState,
    weatherListCities: MutableList<WeatherDTO>,
    navController: NavController
) {
    when (searchState.searchDisplay) {
        SearchDisplay.InitialResults -> weatherListCities.toList()
        SearchDisplay.NoResults -> weatherListCities.clear()
        SearchDisplay.Results -> weatherListCities.toList()
    }
    CitiesListView(
        weatherListCities = weatherListCities,
        navController = navController
    )
}

/**
 * The method is called if the user has received the weather data successfully. see the [RenderWeatherData] rendering method (Success).
 * The method creates a list of cities with weather based on data that came from a local or remote repository.
 * @param weatherListCities - List of cities with weather.
 * @param navController - Navigation Controller for interaction between Home Screen and Details Screen
 */

@Composable
private fun CitiesListView(
    weatherListCities: List<WeatherDTO>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        itemsIndexed(weatherListCities) { _, item ->
            Surface(modifier = Modifier
                .clickable {
                    val cityDataBundle = Bundle()
                    cityDataBundle.putParcelable(WEATHER_DATA_KEY, item)
                    navController.navigate(
                        Screen.DetailWeatherScreen.route,
                        cityDataBundle
                    )
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
 * View the method [getCurrentLocation] and [checkLocationPermission]
 * @param searchState - State of Search [SearchDisplay]
 * @param geocoder - Needed to get the coordinates of the city by name
 */


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    searchState: SearchState,
    context: Context = LocalContext.current,
    geocoder: MutableState<Geocoder> = remember {
        mutableStateOf(Geocoder(context))
    }
) {
    val requestPermissions =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value) {
                    getCurrentLocation(context, mainViewModel)
                }
            }
        }
    SearchBar(
        query = searchState.query,
        onQueryChange = { searchState.query = it },
        onSearchFocusChange = { searchState.focused = it },
        onClearQuery = { searchState.query = TextFieldValue("") },
        onBack = {
            searchState.query = TextFieldValue("")
            mainViewModel.getWeatherFromLocalDataBase()
        },
        searching = searchState.searching,
        focused = searchState.focused,
        modifier = modifier,
        searchHint = stringResource(id = R.string.search_hint),
        location = {
            checkLocationPermission(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissions,
                mainViewModel
            )
        }
    )
    LaunchedEffect(searchState.query.text) {
        searchState.searching = true
        delay(500)
        if (searchState.query.text.length > 2) {
            mainViewModel.getCoordinationByCity(
                query = searchState.query.text,
                geocoder = geocoder.value
            )
        }
        searchState.searching = false
    }
}