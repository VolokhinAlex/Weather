package com.example.java.android1.weather.view

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*

/**
 * The class needed to navigate between screens
 * The following sealed class the screens on which you can navigate
 */

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "home_screen")
    object DetailWeatherScreen: Screen(route = "detail_weather_screen")
}

/**
 * Extensions for the NavController class, for convenient data transfer between screens using bundle
 * Thanks to this, any parcelable objects can be transferred between screens
 */

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}
