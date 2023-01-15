package com.example.java.android1.weather.view.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun MessageDialog(
    modifier: Modifier,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable() (() -> Unit)? = null,
    boxColor: Color = AlertDialogDefaults.containerColor,
    shape: Shape = AlertDialogDefaults.shape
) {
    val dialogState = remember {
        mutableStateOf(true)
    }
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            confirmButton = confirmButton,
            dismissButton = dismissButton,
            title = title,
            text = text,
            containerColor = boxColor,
            shape = shape
        )
    }
}