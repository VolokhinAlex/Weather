package com.example.java.android1.weather.view.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.model.Weather

class MainFragmentAdapter(private var onItemClickListener: ((Weather) -> Unit)?) :
    RecyclerView.Adapter<MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(weather: List<Weather>) {
        weatherData = weather
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.bind(weatherData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = weatherData.size

}