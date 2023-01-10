package com.example.java.android1.weather.view

sealed class LanguageQuery(val languageQuery: String) {
    object EN : LanguageQuery("en_EN")
}
