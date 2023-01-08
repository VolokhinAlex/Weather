package com.example.java.android1.weather.app

import com.example.java.android1.weather.app.AppState.*

/**
 * The class for determining the response status from the user
 * The class contains 2 states.
 * 1. [Success]         ->  If user has confirmed the location permission, then Success State is returned with his coordinates
 * 2. [NotEnabledGPS]   ->  If the user has not turned on the GPS tracker
 */

sealed class LocationState {
    data class Success(val lat: Double, val lon: Double) : LocationState()
    data class NotEnabledGPS(val message: String) : LocationState()
}
