package com.example.java.android1.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityDataRoom(
    val city: String,
    val country: String,
    val lat: Double?,
    val lon: Double?
) : Parcelable
