package com.example.java.android1.weather.view.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ContactsViewModel(
    val contactsLiveData: MutableLiveData<ContactsState> = MutableLiveData()
) : ViewModel() {

    init {
        contactsLiveData.value = ContactsState.Loading
    }
    
    fun filter(query: String, contactsList: List<Contacts>) {
        val filterList = LinkedList<Contacts>()
        contactsList.forEach {
            it.name?.let { name ->
                if (name.lowercase().contains(query.lowercase())) {
                    filterList.add(Contacts(name, it.phone))
                }
            }
        }
        contactsLiveData.value = ContactsState.Success(filterList.toList())
    }

}