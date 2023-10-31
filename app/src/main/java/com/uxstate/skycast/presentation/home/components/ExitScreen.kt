package com.uxstate.skycast.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.presentation.utils.SkyButton
import com.uxstate.skycast.presentation.utils.SkyDialog
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.DialogType

@Composable
fun ExitScreen(
    modifier: Modifier = Modifier,
    isShowDialog: Boolean,
    onExit: () -> Unit,
    onContinue: () -> Unit,
    dialogType: DialogType,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit,
    onCancelDialog: () -> Unit
) {


    if (isShowDialog) {

        SkyDialog(
                onConfirmDialog = onConfirmDialog,
                onCancelDialog = onCancelDialog,
                onDismissDialog = onDismissDialog,
                dialogType = dialogType
        )
    }
    val spacing = LocalSpacing.current
    Box(
            modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.spaceLarge, vertical = spacing.spaceExtraLarge),
            contentAlignment = Alignment.BottomStart
    ) {

        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {

            SkyButton(text = "Exit", onClickButton = onExit)
            SkyButton(text = "Continue", onClickButton = onContinue)
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight1() {

    SkyCastTheme {

        Column {
            ExitScreen(onExit = { },
                    onContinue = {},
                    isShowDialog = true,
                    dialogType = DialogType.PERMISSION,
                    onDismissDialog = {},
                    onConfirmDialog = {},
                    onCancelDialog = {})

        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight2() {

    SkyCastTheme {

        Column {
            ExitScreen(onExit = { },
                    onContinue = {},
                    isShowDialog = false,
                    dialogType = DialogType.PERMISSION,
                    onDismissDialog = {},
                    onConfirmDialog = {},
                    onCancelDialog = {})

        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark1() {

    SkyCastTheme {

        ExitScreen(onExit = { },
                onContinue = {},
                dialogType = DialogType.LOCATION,
                isShowDialog = true,
                onDismissDialog = {},
                onConfirmDialog = {},
                onCancelDialog = {})
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark2() {

    SkyCastTheme {

        ExitScreen(onExit = { },
                onContinue = {},
                dialogType = DialogType.LOCATION,
                isShowDialog = false,
                onDismissDialog = {},
                onConfirmDialog = {},
                onCancelDialog = {})
    }
}