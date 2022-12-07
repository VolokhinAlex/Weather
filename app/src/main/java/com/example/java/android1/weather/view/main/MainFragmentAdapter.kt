package com.example.java.android1.weather.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.Weather

class MainFragmentAdapter(private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(weather: List<Weather>) {
        weatherData = weather
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.bind(weather = weatherData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = weatherData.size

}

interface OnItemClickListener {
    fun onItemClickListener(weather: Weather)
}