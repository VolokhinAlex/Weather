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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.java.android1.weather.model.HoursDTO

@Composable
fun HourlyWeatherItem(weather: HoursDTO) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        Text(text = "${weather.hour}:00", fontSize = 18.sp)
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
        Text(text = "${weather.temp}°", fontSize = 18.sp)
    }

}

@Preview(showBackground = true)
@Composable
fun MyViewPreview() {
    HourlyWeatherItem(HoursDTO("8:00", 0L, 20, 20, "", ""))
}