package com.uxstate.skycast.presentation.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LocationDialog(
    text: String,
    onDismissDialog: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {

    var isShowDialog by remember {
        mutableStateOf(true)
    }


    if (isShowDialog){
        AlertDialog(
                onDismissRequest = { onDismissDialog
                    isShowDialog = false

                                   },
                title = { Text(text = "Error") },
                text = { Text(text = text) },
                confirmButton = {
                    TextButton(onClick = { onPositiveButtonClick ()

                        isShowDialog = false
                    }) {

                        Text(text = "OK")
                    }
                },

                dismissButton = {
                    TextButton(onClick = { onNegativeButtonClick

                        isShowDialog = false
                    }) {

                        Text(text = "No, thanks")
                    }
                }


        )

    }

}