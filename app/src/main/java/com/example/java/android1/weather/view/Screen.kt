package com.example.java.android1.weather.view

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*
import com.example.java.android1.weather.model.WeatherDTO
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "home_screen")
    object DetailWeatherScreen: Screen(route = "detail_weather_screen")
}

//class AssetParamType : NavType<WeatherDTO>(isNullableAllowed = false) {
//    override fun get(bundle: Bundle, key: String): WeatherDTO? {
//        return bundle.getParcelable(key)
//    }
//
//    override fun parseValue(value: String): WeatherDTO {
//        return Gson().fromJson(value, WeatherDTO::class.java)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: WeatherDTO) {
//        bundle.putParcelable(key, value)
//    }
//}

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
