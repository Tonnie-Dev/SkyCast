package com.uxstate.skycast.presentation.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.uxstate.skycast.R

@Composable
fun LocationDialog(

    onPositiveButtonClick: () -> Unit,

) {

    var isShowDialog by remember { mutableStateOf(true) }


    if (isShowDialog){

        AlertDialog(

            title = { Text(text = stringResource(R.string.location_services_offline)) },
            text = { Text(text = stringResource(R.string.enable_location_text)) },
            onDismissRequest = {       isShowDialog = false},
            confirmButton = {
                TextButton(onClick = {
                    onPositiveButtonClick()
                    isShowDialog = false
                }) {

                        Text(text = "OK")
                    }
                },

                dismissButton = {
                    TextButton(onClick = {

                        isShowDialog = false

                    }) {

                        Text(text = "No, thanks")
                    }
                }


        )

    }


}

