package com.example.java.android1.weather.view.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.java.android1.weather.app.App

@Composable
fun ContactsScreen() {
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value) {
                    getContacts()
                }
            }
        }

    Box(modifier = Modifier.fillMaxWidth()) {
        CheckPermission(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            ),
            requestPermissionLauncher
        )
    }
}

@Composable
private fun ShowContacts(contacts: List<Contacts>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), content = {
        itemsIndexed(contacts) { _, item ->
            Card(
                modifier = Modifier
                    .clickable {
                        item.phone?.let {
                            startActivity(
                                context,
                                Intent(
                                    Intent.ACTION_CALL,
                                    Uri.parse("tel:${it}")
                                ), null
                            )
                        }
                    }
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                item.name?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp
                    )
                }
                item.phone?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }
    })
}

data class Contacts(
    val name: String?,
    val phone: String?
)

@SuppressLint("Range")
fun getContacts(): List<Contacts> {
    val contacts: MutableList<Contacts> = mutableListOf()
    val contentResolver = App.appInstance!!.contentResolver
    val contactCursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.Contacts.DISPLAY_NAME + " ASC"
    )
    contactCursor?.let { cursor ->
        for (i in 0..cursor.count) {
            if (cursor.moveToPosition(i)) {
                val contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val hasPhone =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhone == "1") {
                    val phones = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null

                    )
                    if (phones != null) {
                        while (phones.moveToNext()) {
                            phones.let { phone ->
                                contacts.add(
                                    Contacts(
                                        cursor.getString(
                                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) ?: 0
                                        ),
                                        phone.getString(
                                            phones.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER
                                            )
                                        )
                                    )
                                )
                            }
                        }
                        phones.close()
                    }
                } else {
                    contacts.add(
                        Contacts(
                            cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) ?: 0
                            ),
                            null
                        )
                    )
                }
            }
        }
    }
    contactCursor?.close()
    return contacts
}

@Composable
fun CheckPermission(
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    val context = LocalContext.current
    when {
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        } -> {
            ShowContacts(getContacts())
        }
        else -> {
            Dialog(
                "Доступ к контактам и звонкам",
                "Доступ к контактам нужен, чтобы была возможность отобразить их и осуществлять звонки.",
                launcher
            )
        }
    }
}

@Composable
fun Dialog(
    title: String,
    description: String,
    requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    val dialogState = remember {
        mutableStateOf(true)
    }
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            confirmButton = {
                Button(onClick = {
                    dialogState.value = false
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.CALL_PHONE
                        )
                    )
                }) { Text(text = "Allow") }
            },
            dismissButton = {
                Button(onClick = { dialogState.value = false }) {
                    Text(text = "Deny")
                }
            },
            title = { Text(text = title) },
            text = { Text(text = description) }
        )
    }
}
