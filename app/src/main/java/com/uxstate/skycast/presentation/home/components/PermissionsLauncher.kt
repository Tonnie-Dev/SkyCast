package com.uxstate.skycast.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsLauncher(permissionState:PermissionState) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Request permission")
        }
    }

}