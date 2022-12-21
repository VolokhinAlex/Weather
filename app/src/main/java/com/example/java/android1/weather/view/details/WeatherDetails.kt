package com.example.java.android1.weather.view.details

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.myfirstjetpackcomposeapp.ui.theme.DarkTextColor
import com.example.java.android1.myfirstjetpackcomposeapp.ui.theme.WeatherDetailsBoxColor
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.model.WeatherDTO
import java.util.*


@Composable
fun DetailsWeatherContent(weatherDTO: WeatherDTO) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

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

        val date = Date(weatherDTO.now * 1000L)
        val day = DateFormat.format("EEEE", date)
        val month = DateFormat.format("MMMM", date)
        val calendar = Calendar.getInstance()
        calendar.time = date

        Row {
            Text(text = "${calendar.get(Calendar.DAY_OF_MONTH)} ${month}, ", fontSize = 20.sp)
            Text(text = day.toString(), fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painterResource(id = R.drawable.cloudy),
                contentDescription = "cloudy",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 3.dp)
            )
            Text(text = "${weatherDTO.fact?.temp} °", fontSize = 60.sp, color = DarkTextColor)
            Text(text = "${weatherDTO.fact?.condition}", fontSize = 25.sp)
        }

        Text(text = "today", fontSize = 18.sp, modifier = Modifier.padding(bottom = 20.dp))

        weatherDTO.forecasts?.get(0)?.hours?.let { HourlyListView(it) }

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
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DetailsWeatherContent(WeatherDTO(111L, null, null, null, null))
}