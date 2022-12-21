package com.example.java.android1.weather.view.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.weather.model.Weather

@Composable
fun CityCardView(weather: Weather) {

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
                Text(text = weather.city.city, fontSize = 17.sp)
                Text(text = weather.city.country, fontSize = 17.sp)
            }

            Column(
                Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${weather.temperature}°", fontSize = 30.sp)
                Text(text = weather.condition, fontSize = 17.sp)
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyViewPreview() {
    CityCardView(Weather())
}