package com.example.java.android1.weather.view.details

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.R
import com.example.java.android1.weather.app.WeatherAppState
import com.example.java.android1.weather.model.HoursDTO
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.LanguageQuery
import com.example.java.android1.weather.view.theme.*
import com.example.java.android1.weather.view.widgets.ErrorMessage
import com.example.java.android1.weather.view.widgets.LoadingProgressBar
import com.example.java.android1.weather.viewmodel.DetailsViewModel
import java.util.*

/**
 * The main method for the layout of the details screen methods
 * @param weatherDTO - the data which comes from bundle
 * @param detailsViewModel - Details View Model
 * @param navController - Controller for screen navigation
 */

@Composable
fun DetailsScreen(
    weatherDTO: WeatherDTO?,
    detailsViewModel: DetailsViewModel,
    navController: NavController
) {
    LaunchedEffect(true) {
        weatherDTO?.info?.let {
            detailsViewModel.getWeatherDetailsFromRemoteServer(
                it.lat,
                it.lon,
                LanguageQuery.EN.languageQuery
            )
        }
    }
    detailsViewModel.detailsWeatherData.observeAsState().value?.let { state ->
        RenderDetailsWeatherData(state, navController)
    }
}

/**
 * The method processes state from the remote server
 * @param weatherAppState - The state that came from the remote server. [WeatherAppState]
 * @param navController - To navigate back
 */

@Composable
fun RenderDetailsWeatherData(weatherAppState: WeatherAppState, navController: NavController) {
    when (weatherAppState) {
        is WeatherAppState.Error -> weatherAppState.error.message?.let { ErrorMessage(text = it) }
        WeatherAppState.Loading -> LoadingProgressBar()
        is WeatherAppState.Success -> {
            val weatherListData = weatherAppState.weatherData
            DetailsWeatherContent(
                modifier = Modifier
                    .padding(
                        start = DETAILS_SCREEN_PADDING_START_END,
                        end = DETAILS_SCREEN_PADDING_START_END,
                        top = DETAILS_SCREEN_PADDING_TOP_BOTTOM,
                        bottom = DETAILS_SCREEN_PADDING_TOP_BOTTOM
                    )
                    .fillMaxSize(),
                weatherDTO = weatherListData[0],
                navController = navController
            )
        }
    }
}

/**
 * The method of adding details weather content in the [DetailsScreen]
 * @param weatherDTO - Weather Data From Remote Server
 */

@Composable
fun DetailsWeatherContent(
    modifier: Modifier,
    weatherDTO: WeatherDTO,
    navController: NavController
) {
    LazyColumn(modifier = modifier) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderDetailsScreen(weatherDTO = weatherDTO, navController = navController)
                CenterDetailsScreen(weatherDTO = weatherDTO)
            }
            Text(
                text = stringResource(id = R.string.today),
                fontSize = 18.sp,
                color = DarkTextColor
            )
            weatherDTO.forecasts?.get(0)?.hours?.let { HourlyListView(it) }
        }
        val forecastsForWeekList = weatherDTO.forecasts?.subList(1, weatherDTO.forecasts.size)
        if (forecastsForWeekList != null) {
            itemsIndexed(forecastsForWeekList) { _, item ->
                item.date_ts?.let {
                    val date = Date((it + weatherDTO.info?.tzinfo?.offset!!) * 1000L)
                    val day = DateFormat.format("EEEE", date)
                    ForecastWeatherCard(
                        item.parts?.day?.icon.toString(),
                        day.toString(),
                        item.parts?.day?.temp_avg.toString(),
                        item.parts?.night?.temp_avg.toString()
                    )
                }
            }
        }
        item {
            WeatherAdvancedInfo(weatherDTO = weatherDTO)
        }
    }
}

/**
 * The method of adding a city and date in the header of screen
 * @param weatherDTO - Weather Data From Remote Server
 * @param navController - To navigate back
 */

@Composable
fun HeaderDetailsScreen(weatherDTO: WeatherDTO, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                tint = Color.DarkGray
            )
        }
        Row(modifier = Modifier.padding(start = 40.dp)) {
            Text(
                text = "${weatherDTO.geo_object?.locality?.name}, ${weatherDTO.geo_object?.country?.name}",
                fontSize = DETAILS_PRIMARY_TITLE_SIZE,
                color = DarkTextColor
            )
        }
    }
    val date = Date((weatherDTO.now + weatherDTO.info?.tzinfo?.offset!!) * 1000L)
    val day = DateFormat.format("EEEE", date)
    val month = DateFormat.format("MMMM", date)
    val calendar = Calendar.getInstance()
    calendar.time = date
    Row(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = "${calendar.get(Calendar.DAY_OF_MONTH)} ${month}, $day",
            fontSize = DETAILS_PRIMARY_TEXT_SIZE,
            color = DarkTextColor
        )
    }
}

@Composable
fun CenterDetailsScreen(weatherDTO: WeatherDTO) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact?.icon}.svg")
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = "${weatherDTO.fact?.icon}",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .size(100.dp)
            .padding(top = 5.dp, bottom = 5.dp)
    )
    Text(
        text = "${weatherDTO.fact?.temp}°",
        fontSize = 60.sp,
        color = DarkTextColor,
        modifier = Modifier.padding(bottom = 5.dp)
    )
    Text(
        text = "${weatherDTO.fact?.condition}",
        fontSize = DETAILS_PRIMARY_TITLE_SIZE,
        color = DarkTextColor
    )
}

/**
 * The method of adding a hourly weather in the center of screen
 * @param hours - Hourly data weather From Remote Server
 */

@Composable
fun HourlyListView(hours: List<HoursDTO>) {
    LazyRow {
        itemsIndexed(hours) { _, items ->
            HourlyWeatherItem(items)
        }
    }
}

/**
 * Advanced weather information
 * @param weatherDTO - Weather Data From Remote Server
 */

@Composable
fun WeatherAdvancedInfo(weatherDTO: WeatherDTO) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(15.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(
                        text = stringResource(id = R.string.label_sunrise),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    weatherDTO.forecasts?.get(0)?.sunrise?.let { sunrise ->
                        Text(
                            text = sunrise,
                            fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                            color = DarkTextColor
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.label_sunset),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    weatherDTO.forecasts?.get(0)?.sunset?.let { sunset ->
                        Text(
                            text = sunset,
                            fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                            color = DarkTextColor
                        )
                    }
                }
            }
        }
        Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), color = DarkTextColor)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(
                        text = stringResource(id = R.string.label_feels_like),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    Text(
                        text = "${weatherDTO.fact?.feels_like}°",
                        fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                        color = DarkTextColor
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.label_windy_speed),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    Text(
                        text = "${weatherDTO.fact?.wind_speed}",
                        fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                        color = DarkTextColor
                    )
                }
            }
        }
        Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), color = DarkTextColor)
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(
                        text = stringResource(R.string.label_humidity),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    Text(
                        text = "${weatherDTO.fact?.humidity}%",
                        fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                        color = DarkTextColor
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.label_pressure),
                        fontSize = DETAILS_LABEL_TEXT_SIZE
                    )
                    Text(
                        text = "${weatherDTO.fact?.pressure_mm} mm",
                        fontSize = DETAILS_PRIMARY_TEXT_SIZE,
                        color = DarkTextColor
                    )
                }
            }
        }
    }
}
