package com.uxstate.skycast.presentation.home

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import com.uxstate.skycast.domain.model.WeatherType
import com.uxstate.skycast.presentation.destinations.ForecastScreenDestination
import com.uxstate.skycast.presentation.destinations.SettingsScreenDestination
import com.uxstate.skycast.presentation.home.components.EmptyWeatherBox
import com.uxstate.skycast.presentation.home.components.HomeContent
import com.uxstate.skycast.presentation.home.components.LinearProgressBar
import com.uxstate.skycast.presentation.home.components.LocationDialog
import com.uxstate.skycast.utils.FAHRENHEIT


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

    val startLocationSettings =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val isLoading = state.isLoading
    val isLocationEnabled by viewModel.isLocationEnabled
    val isShowLocationDialog = !isLocationEnabled && state.isShowDialog
    val isFahrenheitUnit = state.appPreferences.tempUnit.toString() == FAHRENHEIT





    LaunchedEffect(key1 = isPermissionGranted, block = { viewModel.refreshWeather() })





    if (isShowLocationDialog) {

        EmptyWeatherBox() { viewModel.onEvent(HomeEvent.OnRetry) }
        LocationDialog(

                onDismissDialog = {
                    viewModel.onEvent(HomeEvent.OnDismissDialog)

                },
                onNegativeButtonClick = { viewModel.onEvent(HomeEvent.OnCancelDialog) },
                onPositiveButtonClick = {


                    viewModel.onEvent(HomeEvent.OnConfirmDialog)
                    val intent =
                        Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startLocationSettings.launch(intent)

                })
    }


    if (state.isLoading) {

        LinearProgressBar()
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
        /*if (!state.isLoading){


        }else {

            Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.size(50.dp))

            }
        }*/

    } ?: kotlin.run {

        /* EmptyWeatherBox() {

             viewModel.onEvent(HomeEvent.OnRetry)
         }*/
    }


}





