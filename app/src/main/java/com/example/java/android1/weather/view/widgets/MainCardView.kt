package com.example.java.android1.weather.view.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.theme.CARD_PRIMARY_PADDING
import com.example.java.android1.weather.view.theme.CARD_PRIMARY_TEXT_SIZE

@Composable
fun CityCardView(weather: WeatherDTO) {
    Card(modifier = Modifier.padding(bottom = 15.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.padding(CARD_PRIMARY_PADDING)) {
                Text(
                    text = weather.geo_object?.locality?.name.toString(),
                    fontSize = CARD_PRIMARY_TEXT_SIZE
                )
                Text(
                    text = weather.geo_object?.country?.name.toString(),
                    fontSize = CARD_PRIMARY_TEXT_SIZE
                )
            }
            Column(
                Modifier.padding(CARD_PRIMARY_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${weather.fact?.temp}°", fontSize = 30.sp)
                Text(text = weather.fact?.condition.toString(), fontSize = CARD_PRIMARY_TEXT_SIZE)
            }
        }
    }
}
