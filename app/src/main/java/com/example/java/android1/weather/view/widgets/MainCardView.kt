package com.example.java.android1.weather.view.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.weather.model.WeatherDTO

@Composable
fun CityCardView(weather: WeatherDTO) {
    Card(modifier = Modifier.padding(bottom = 15.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                Modifier.padding(10.dp),
            ) {
                Text(text = weather.geo_object?.locality?.name.toString(), fontSize = 17.sp)
                Text(text = weather.geo_object?.country?.name.toString(), fontSize = 17.sp)
            }

            Column(
                Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${weather.fact?.temp}°", fontSize = 30.sp)
                Text(text = weather.fact?.condition.toString(), fontSize = 17.sp)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyViewPreview() {
    CityCardView(WeatherDTO(1L, null, null, null, null))
}