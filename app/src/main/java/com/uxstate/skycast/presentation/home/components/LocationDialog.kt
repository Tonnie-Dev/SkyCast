package com.uxstate.skycast.presentation.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun LocationDialog(
    text: String,
    onDismissDialog: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {


    AlertDialog(
            onDismissRequest = onDismissDialog,
            title = { Text(text = "Error") },
            text = { Text(text = text) },
            confirmButton = {
                TextButton(onClick = onPositiveButtonClick) {

                    Text(text = "OK")
                }
            }, dismissButton = {
        TextButton(onClick = onPositiveButtonClick) {

            Text(text = "No, thanks")
        }
    }


    )
}