package com.example.java.android1.weather.view.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.HoursDTO

@Composable
fun HourlyWeatherItem(weather: HoursDTO) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
        Text(text = "${weather.hour}:00", fontSize = 18.sp)
        Image(
            painter = painterResource(id = R.drawable.cloudy),
            contentDescription = "weather Icon",
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