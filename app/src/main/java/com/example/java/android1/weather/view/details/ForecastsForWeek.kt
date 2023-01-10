package com.example.java.android1.weather.view.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.view.theme.DarkTextColor
import com.example.java.android1.weather.view.theme.DarkTextColor50
import com.example.java.android1.weather.view.theme.FORECASTS_CARD_PRIMARY_PADDING
import com.example.java.android1.weather.view.theme.FORECASTS_CARD_TEXT_SIZE

@Composable
fun ForecastWeatherCard(icon: String, day: String, dayTempAvg: String, nightTempAvg: String) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = FORECASTS_CARD_PRIMARY_PADDING)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = FORECASTS_CARD_PRIMARY_PADDING,
                    end = FORECASTS_CARD_PRIMARY_PADDING
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = day,
                fontSize = FORECASTS_CARD_TEXT_SIZE,
                color = DarkTextColor,
                modifier = Modifier.padding(end = 25.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://yastatic.net/weather/i/icons/funky/dark/${icon}.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "weather icon",
                modifier = Modifier.size(50.dp)
            )
            Row(modifier = Modifier.padding(start = 25.dp)) {
                Text(
                    text = "${dayTempAvg}°",
                    fontSize = FORECASTS_CARD_TEXT_SIZE,
                    color = DarkTextColor,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    text = "${nightTempAvg}°",
                    fontSize = FORECASTS_CARD_TEXT_SIZE,
                    color = DarkTextColor50
                )
            }
        }
    }
}
