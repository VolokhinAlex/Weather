package com.example.java.android1.weather.view.main

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.Weather

class MainFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val city: TextView
    private val country: TextView
    private val temperature: TextView
    private val condition: TextView

    init {
        city = itemView.findViewById(R.id.card_city_text_item)
        country = itemView.findViewById(R.id.card_city_country_item)
        temperature = itemView.findViewById(R.id.card_city_temperature_item)
        condition = itemView.findViewById(R.id.card_city_temperature_condition)
    }

    fun bind(weather: Weather, onItemClickListener: ((Weather) -> Unit)?) {
        city.text = weather.city.city
        country.text = weather.city.country
        temperature.text = String.format("%s °", weather.temperature)
        condition.text = weather.condition
        itemView.setOnClickListener {
            onItemClickListener?.invoke(weather)
        }
    }

}