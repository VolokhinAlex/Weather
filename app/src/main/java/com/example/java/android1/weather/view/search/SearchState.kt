package com.example.java.android1.weather.view.search

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.example.java.android1.weather.model.WeatherDTO

/**
 * The class needed to remember search states
 */

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<WeatherDTO>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)

    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.InitialResults
            searchResults.isEmpty() -> SearchDisplay.NoResults
            searchResults.isNotEmpty() && query.text.isNotEmpty() -> SearchDisplay.Results
            else -> SearchDisplay.NoResults
        }

}