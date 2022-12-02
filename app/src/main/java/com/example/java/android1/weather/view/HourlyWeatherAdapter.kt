package com.example.java.android1.weather.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R

class HourlyWeatherAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<HourlyWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val view: View = inflater.inflate(R.layout.hourly_weather_item, parent, false)
        return HourlyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 7
    }

}