package com.example.java.android1.weather.view.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.model.HoursDTO
import com.example.java.android1.weather.view.theme.DarkTextColor
import com.example.java.android1.weather.view.theme.HOURLY_CARD_TEXT_SIZE

@Composable
fun HourlyWeatherItem(weather: HoursDTO) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        Text(text = "${weather.hour}:00", fontSize = HOURLY_CARD_TEXT_SIZE, color = DarkTextColor)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = "${weather.icon}",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .size(50.dp)
        )
        Text(text = "${weather.temp}°", fontSize = HOURLY_CARD_TEXT_SIZE, color = DarkTextColor)
    }
}
