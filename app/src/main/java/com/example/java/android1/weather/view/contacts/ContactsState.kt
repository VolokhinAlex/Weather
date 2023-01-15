package com.example.java.android1.weather.view.contacts

sealed class ContactsState {
    data class Success(val data: List<Contacts>) : ContactsState()
    data class Error(val error: Throwable) : ContactsState()
    object Loading : ContactsState()
}