package com.uxstate.skycast.presentation.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.uxstate.skycast.R

@Composable
fun LocationDialog(
    onDismissDialog: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {

    AlertDialog(

            title = { Text(text = stringResource(R.string.location_services_offline)) },
            text = { Text(text = stringResource(R.string.enable_location_text)) },
            onDismissRequest = { onDismissDialog() },
            confirmButton = {
                TextButton(onClick = {
                    onPositiveButtonClick()

                }) {

                    Text(text = "OK")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    onNegativeButtonClick()


                }) {

                    Text(text = "No, thanks")
                }
            }


    )

}

