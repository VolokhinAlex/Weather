package com.example.java.android1.weather.view.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.HoursDTO

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherViewHolder>() {

    private var data: List<HoursDTO> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<HoursDTO>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        return HourlyWeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}