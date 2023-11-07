package com.uxstate.skycast.presentation.home

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.presentation.home.components.LoadHomeContent
import com.uxstate.skycast.presentation.home.components.ShowExitScreen
import com.uxstate.skycast.presentation.home.components.ShowLinearLoadingBar
import com.uxstate.skycast.utils.DialogType.*


@RequiresApi(Build.VERSION_CODES.P)
@RootNavGraph(start = true)
@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {

    val state by viewModel.state.collectAsState()

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val isPermGranted = permissionState.status.isGranted

    val isLocationEnabled by viewModel.isLocationEnabled
    val isLocationNull = state.isLocationNull
    val hasLocationData = isLocationEnabled && !isLocationNull

    val isLoading = state.isLoading
    val isShowDialog = state.isShowDialog


    Box(
            modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .fillMaxSize()
    )

    when {

        !isPermGranted -> ShowExitScreen(isShowDialog, PERMISSION, permissionState, viewModel)
        !hasLocationData -> ShowExitScreen(isShowDialog, LOCATION, permissionState, viewModel)
        isLoading -> ShowLinearLoadingBar()
        else -> LoadHomeContent(viewModel = viewModel, navigator = navigator)
    }

}





