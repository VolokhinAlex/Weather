package com.example.java.android1.weather.view.search

/**
 * Search States
 * [InitialResults] - Initialization status. Occurs when the search screen is opened for the first
 * time, and when the user exits the search focus
 * [Results]        - The state of the result occurs when the response is received from the server
 * [NoResults]      - The state with no result occurs when you enter text in the search text field
 */

enum class SearchDisplay {
    InitialResults, Results, NoResults
}