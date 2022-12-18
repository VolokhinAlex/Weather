package com.example.java.android1.weather.view.details

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.HoursDTO

class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: AppCompatImageView
    private val time: TextView
    private val temperature: TextView

    init {
        image = itemView.findViewById(R.id.card_image)
        time = itemView.findViewById(R.id.card_time)
        temperature = itemView.findViewById(R.id.card_temperature)
    }

    fun bind(dataHours: HoursDTO) {
        time.text = "${dataHours.hour}:00"
        temperature.text = "${dataHours.temp}°"
    }

}