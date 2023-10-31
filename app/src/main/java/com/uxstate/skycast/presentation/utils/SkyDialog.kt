package com.uxstate.skycast.presentation.utils

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.DialogType

@Composable
fun SkyDialog(

    onConfirmDialog: () -> Unit,
    dialogType: DialogType

) {

    var isShowDialog by remember { mutableStateOf(true) }


    if (isShowDialog) {

        AlertDialog(

                title = { Text(text = stringResource(id = dialogType.dialogTitle)) },
                text = { Text(text = stringResource(id = dialogType.dialogText)) },
                onDismissRequest = {

                    isShowDialog = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        onConfirmDialog()
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


@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun SkyDialogPreviewLight() {

    SkyCastTheme {

        Column {
            SkyDialog(
                    onConfirmDialog = { /*TODO*/ },

                    dialogType = DialogType.LOCATION
            )




        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun SkyDialogPreviewDark() {


    SkyCastTheme {

        Column {
            SkyDialog(
                    onConfirmDialog = { /*TODO*/ },

                    dialogType = DialogType.PERMISSION
            )




        }
    }
}

