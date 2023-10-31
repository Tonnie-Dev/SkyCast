package com.uxstate.skycast.presentation.home.components

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.finishAffinity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.uxstate.skycast.presentation.home.HomeEvent
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.presentation.utils.SkyButton
import com.uxstate.skycast.presentation.utils.SkyDialog
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.DialogType

@Composable
fun ExitScreen(
    modifier: Modifier = Modifier,
    dialogType: DialogType,
    onConfirmDialog: () -> Unit,
    onContinue: () -> Unit,
    onExit: () -> Unit

) {
    val spacing = LocalSpacing.current
    var isShowDialog by remember {
        mutableStateOf(true)
    }

    if (isShowDialog) {

        SkyDialog(
                dialogType = dialogType,
                onConfirmDialog = { onConfirmDialog(); isShowDialog = false},
                onCancelDialog = {isShowDialog = false},
                onDismissDialog = {isShowDialog = false}

        )
    }

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


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowExitScreen(
    dialogType: DialogType,
    permissionState: PermissionState,
    viewModel: HomeViewModel,
) {

    //val activity = (LocalContext.current as Activity)
    val context = LocalContext.current
    val startLocationSettings =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    ExitScreen(

            dialogType = dialogType,
            onConfirmDialog = {

                when (dialogType) {

                    DialogType.PERMISSION -> {

                        permissionState.launchPermissionRequest()
                    }

                    DialogType.LOCATION -> {

                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startLocationSettings.launch(intent)
                    }

                }
            },


            onContinue = { viewModel.onEvent(HomeEvent.OnContinue) },
            onExit = {


                     viewModel.onEvent(HomeEvent.OnExit(context = context))
                /*finishAffinity(activity) */

                     },
    )

}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight1() {

    SkyCastTheme {

        Column {
            ExitScreen(
                    onExit = { },
                    onContinue = {},
                    dialogType = DialogType.PERMISSION,

                    onConfirmDialog = {},
            )

        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight2() {

    SkyCastTheme {

        Column {
            ExitScreen(
                    onExit = { },
                    onContinue = {},

                    dialogType = DialogType.PERMISSION,

                    onConfirmDialog = {},
            )

        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark1() {

    SkyCastTheme {

        ExitScreen(
                onExit = { },
                onContinue = {},
                dialogType = DialogType.LOCATION,


                onConfirmDialog = {},
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark2() {

    SkyCastTheme {

        ExitScreen(
                onExit = { },
                onContinue = {},
                dialogType = DialogType.LOCATION,

                onConfirmDialog = {},
        )
    }
}