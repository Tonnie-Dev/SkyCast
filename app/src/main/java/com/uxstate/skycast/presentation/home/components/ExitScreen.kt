package com.uxstate.skycast.presentation.home.components

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.uxstate.skycast.presentation.home.HomeEvent.*
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.presentation.utils.SkyButton
import com.uxstate.skycast.presentation.utils.SkyDialog
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.DialogType
import timber.log.Timber

@Composable
fun ExitScreen(
    modifier: Modifier = Modifier,
    isShowDialog: Boolean,
    dialogType: DialogType,
    onConfirmDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onContinue: () -> Unit,
    onExit: () -> Unit

) {
    val spacing = LocalSpacing.current

    if (isShowDialog) {
        SkyDialog(
                dialogType = dialogType,
                onConfirmDialog = { onConfirmDialog() },
                onCancelDialog = { onCancelDialog() },
                onDismissDialog = { onDismissDialog() }
        )
    }

    AnimatedVisibility(visible = !isShowDialog) {

        Box(
                modifier = modifier
                        .fillMaxSize()
                        .padding(
                                horizontal = spacing.spaceLarge,
                                vertical = spacing.spaceExtraLarge
                        ),
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


}


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowExitScreen(
    isShowDialog: Boolean,
    dialogType: DialogType,
    permissionState: PermissionState,
    viewModel: HomeViewModel,
) {

    val context = LocalContext.current

    val startLocationSettings =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    ExitScreen(
            isShowDialog = isShowDialog,
            dialogType = dialogType,

            onConfirmDialog = {

                when (dialogType) {

                    DialogType.PERMISSION -> {

                        permissionState.launchPermissionRequest()
                        viewModel.onEvent(OnConfirmDialog)

                        Timber.i("Exit Screen - permission launch requested")
                    }

                    DialogType.LOCATION -> {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startLocationSettings.launch(intent)
                        viewModel.onEvent(OnConfirmDialog)
                    }

                }
            },

            onCancelDialog = { viewModel.onEvent(OnCancelDialog) },
            onDismissDialog = { viewModel.onEvent(OnDismissDialog) },

            onContinue = {

                if (permissionState.status.isGranted) {

                    viewModel.onEvent(OnContinue(isPermissionGranted = true))

                } else {

                    viewModel.onEvent(OnContinue(isPermissionGranted = false))
                    Timber.i("ExitScreen-else - ${permissionState.status.isGranted}")
                }


            },

            onExit = {

                viewModel.onEvent(OnExit(context = context))

            },
    )

}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight1() {

    SkyCastTheme {

        ExitScreen(
                dialogType = DialogType.PERMISSION,
                onConfirmDialog = {},
                onCancelDialog = {},
                onDismissDialog = {},
                onContinue = {},
                onExit = { },
                isShowDialog = true
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun ExitScreenPreviewLight2() {

    SkyCastTheme {

        ExitScreen(
                dialogType = DialogType.PERMISSION,
                onConfirmDialog = {},
                onCancelDialog = {},
                onDismissDialog = {},
                onContinue = {},
                onExit = { },
                isShowDialog = true
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark1() {

    SkyCastTheme {


        ExitScreen(
                dialogType = DialogType.PERMISSION,
                onConfirmDialog = {},
                onCancelDialog = {},
                onDismissDialog = {},
                onContinue = {},
                onExit = {},
                isShowDialog = true
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun ExitScreenPreviewDark2() {

    SkyCastTheme {

        ExitScreen(
                dialogType = DialogType.PERMISSION,
                onConfirmDialog = {},
                onCancelDialog = {},
                onDismissDialog = {},
                onContinue = {},
                onExit = {},
                isShowDialog = true
        )
    }
}