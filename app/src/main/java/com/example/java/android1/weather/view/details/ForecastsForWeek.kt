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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.view.theme.DarkTextColor
import com.example.java.android1.weather.view.theme.DarkTextColor50

@Composable
fun ForecastWeatherCard(icon: String, day: String, dayTempAvg: String, nightTempAvg: String) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = day,
                fontSize = 18.sp,
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
                    fontSize = 18.sp,
                    color = DarkTextColor,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(text = "${nightTempAvg}°", fontSize = 18.sp, color = DarkTextColor50)
            }
        }
    }
}
