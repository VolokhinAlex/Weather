package com.example.java.android1.weather.view.details

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.model.HoursDTO
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.main.Loading
import com.example.java.android1.weather.view.main.NotFoundData
import com.example.java.android1.weather.view.theme.DarkTextColor
import com.example.java.android1.weather.view.theme.WeatherDetailsBoxColor
import com.example.java.android1.weather.viewmodel.AppState
import com.example.java.android1.weather.viewmodel.DetailsViewModel
import java.util.*

@Composable
fun DetailsScreen(weatherDTO: WeatherDTO?) {
    val viewModel by remember {
        mutableStateOf(DetailsViewModel())
    }
    val detailsViewModel by viewModel.detailsLiveData.observeAsState()
    LaunchedEffect(Unit) {
        weatherDTO?.info?.let {
            viewModel.getWeatherDetailFromRemoteServer(
                it.lat,
                it.lon,
                Locale.getDefault().toString()
            )
        }
    }
    detailsViewModel?.let {
        RenderData(it)
    }
}

@Composable
fun DetailsWeatherContent(
    weatherDTO: WeatherDTO,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WeatherDTOTopScreenInfo(weatherDTO = weatherDTO)

            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
            ) {
                WeatherCenterScreenInfo(weatherDTO = weatherDTO)
            }

            Text(text = "today", fontSize = 18.sp, modifier = Modifier.padding(bottom = 20.dp))

            weatherDTO.forecasts?.get(0)?.hours?.let { HourlyListView(it) }

        }

        val forecastsForWeekList = weatherDTO.forecasts?.subList(1, weatherDTO.forecasts?.size)
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
            WeatherDetailInfo(weatherDTO = weatherDTO)
        }

    }
}

@Composable
fun RenderData(appState: AppState) {
    when (appState) {
        is AppState.Error -> {
            NotFoundData()
        }
        AppState.Loading -> {
            Loading()
        }
        is AppState.Success -> {
            val weatherDTO = appState.weatherData
            DetailsWeatherContent(weatherDTO = weatherDTO[0])
        }
    }
}

@Composable
fun HourlyListView(hours: List<HoursDTO>) {
    LazyRow {
        itemsIndexed(hours) { _, items ->
            HourlyWeatherItem(items)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DetailsWeatherContent(WeatherDTO(111L, null, null, null, null))
}

@Composable
fun WeatherDetailInfo(weatherDTO: WeatherDTO) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = WeatherDetailsBoxColor, shape = RoundedCornerShape(15.dp))
            .padding(15.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = "Sunrise", fontSize = 13.sp)
                    weatherDTO.forecasts?.get(0)?.sunrise?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            color = DarkTextColor
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Sunset", fontSize = 13.sp)
                    weatherDTO.forecasts?.get(0)?.sunset?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
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
                    Text(text = "Feels like", fontSize = 13.sp)
                    Text(
                        text = "${weatherDTO.fact?.feels_like}°",
                        fontSize = 20.sp,
                        color = DarkTextColor
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Windy", fontSize = 13.sp)
                    Text(
                        text = "${weatherDTO.fact?.wind_speed}",
                        fontSize = 20.sp,
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
                    Text(text = "Humidity", fontSize = 13.sp)
                    Text(
                        text = "${weatherDTO.fact?.humidity}%",
                        fontSize = 20.sp,
                        color = DarkTextColor
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Pressure", fontSize = 13.sp)
                    Text(
                        text = "${weatherDTO.fact?.pressure_mm} mm",
                        fontSize = 20.sp,
                        color = DarkTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCenterScreenInfo(weatherDTO: WeatherDTO) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact?.icon}.svg")
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = "${weatherDTO.fact?.icon}",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .size(100.dp)
            .padding(bottom = 3.dp)
    )
    Text(text = "${weatherDTO.fact?.temp} °", fontSize = 60.sp, color = DarkTextColor)
    Text(text = "${weatherDTO.fact?.condition}", fontSize = 25.sp)
}

@Composable
fun WeatherDTOTopScreenInfo(weatherDTO: WeatherDTO) {
    Row {
        Text(
            text = "${weatherDTO.geo_object?.locality?.name}, ",
            fontSize = 25.sp,
            color = DarkTextColor
        )
        Text(
            text = "${weatherDTO.geo_object?.country?.name}",
            fontSize = 25.sp,
            color = DarkTextColor
        )
    }
    val date = Date((weatherDTO.now + weatherDTO.info?.tzinfo?.offset!!) * 1000L)
    val day = DateFormat.format("EEEE", date)
    val month = DateFormat.format("MMMM", date)
    val calendar = Calendar.getInstance()
    calendar.time = date

    Row {
        Text(text = "${calendar.get(Calendar.DAY_OF_MONTH)} ${month}, ", fontSize = 20.sp)
        Text(text = day.toString(), fontSize = 20.sp)
    }
}