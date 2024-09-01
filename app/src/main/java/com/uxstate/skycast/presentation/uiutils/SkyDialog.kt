package com.uxstate.skycast.presentation.uiutils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.DialogType

@Composable
fun SkyDialog(
    dialogType: DialogType,
    onConfirmDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    onDismissDialog: () -> Unit,
) {
    var isShowDialog by remember { mutableStateOf(true) }

    AlertDialog(
        title = { Text(text = stringResource(id = dialogType.dialogTitle)) },
        text = { Text(text = stringResource(id = dialogType.dialogText)) },
        confirmButton = {
            TextButton(onClick = {
                onConfirmDialog()
                isShowDialog = false
            }) {
                Text(text = stringResource(id = R.string.ok_text))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancelDialog()
                isShowDialog = false
            }) {
                Text(text = stringResource(R.string.no_thanks_text))
            }
        },
        onDismissRequest = {
            onDismissDialog()
            isShowDialog = false
        },
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun SkyDialogPreviewLight() {
    SkyCastTheme {
        Column {
            SkyDialog(
                dialogType = DialogType.LOCATION,
                onConfirmDialog = { },
                onCancelDialog = {},
                onDismissDialog = {},
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
                dialogType = DialogType.LOCATION,
                onConfirmDialog = { },
                onCancelDialog = {},
                onDismissDialog = {},
            )
        }
    }
}
