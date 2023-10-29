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




        AlertDialog(
                onDismissRequest = { onDismissDialog()


                                   },
                title = { Text(text = "Location is Off") },
                text = { Text(text = "Press OK to enable Location") },
                confirmButton = {
                    TextButton(onClick = { onPositiveButtonClick ()


                    }) {

                        Text(text = "OK")
                    }
                },

                dismissButton = {
                    TextButton(onClick = { onNegativeButtonClick()


                    }) {

                        Text(text = "No, thanks")
                    }
                }


        )

    }

