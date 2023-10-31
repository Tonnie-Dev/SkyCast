package com.uxstate.skycast.presentation.home

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.presentation.home.components.LoadHomeContent
import com.uxstate.skycast.presentation.home.components.PermissionsLauncher
import com.uxstate.skycast.presentation.home.components.ShowLinearLoadingBar


@RequiresApi(Build.VERSION_CODES.P)
@RootNavGraph(start = true)
@Destination
@Composable
@OptIn(
        ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class
)

fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    val state by viewModel.state.collectAsState()


    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val isPermissionGranted = permissionState.status.isGranted

    val isLocationEnabled by viewModel.isLocationEnabled
    val isLoading = state.isLoading


    LaunchedEffect(key1 = isLocationEnabled, block = { viewModel.refreshWeather() })


    Column {

        if (isPermissionGranted) {


           if (isLocationEnabled) {


               if (isLoading){
                   ShowLinearLoadingBar()

               }
               else {
                   LoadHomeContent(viewModel = viewModel, navigator = navigator)

               }
            }

            else {


            }
        } else {

            PermissionsLauncher(permissionState = permissionState)
        }
    }


   /* Column {

        if (state.isLoading) {

            LinearProgressBar()
        }
        if (isShowLocationDialog) {

            LocationDialog(

                    onPositiveButtonClick = {


                        val intent =
                            Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startLocationSettings.launch(intent)

                    })
        }


        // TODO: Check on this null
        state.currentWeather?.let {


            HomeContent(
                    isLoading = isLoading,
                    isFahrenheitUnit = isFahrenheitUnit,
                    currentWeather = it,
                    icon = WeatherType.fromWMO(it.networkWeatherDescription.first().icon).icon,
                    onForecastButtonClick = {
                        navigator.navigate(
                                ForecastScreenDestination
                        )
                    },
                    navigateToSettings = {
                        navigator.navigate(
                                SettingsScreenDestination
                        )
                    }, onRefreshWeather = viewModel::refreshWeather


            )


        } ?: run {

            PermissionsLauncher(permissionState = permissionState)
        }
    }*/


}





