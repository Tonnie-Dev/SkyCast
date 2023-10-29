package com.uxstate.skycast.presentation.home

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.uxstate.skycast.presentation.home.components.LocationDialog
import com.uxstate.skycast.utils.FAHRENHEIT



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

    val isShowLocationDialog = state.isLocationNull && state.isShowDialog

    val isFahrenheitUnit = state.appPreferences.tempUnit.toString() == FAHRENHEIT


    val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = viewModel::refreshWeather
    )


    LaunchedEffect(key1 = isPermissionGranted, block = {viewModel.refreshWeather()})

    Box(
            modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
    ) {



            if (isShowLocationDialog) {

                EmptyWeatherBox() {

                    viewModel.onEvent(HomeEvent.OnRetry)
                }
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


            // TODO: Check on this null
            state.currentWeather?.let {


                HomeContent(
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
                        },


                        )
                /*if (!state.isLoading){


                }else {

                    Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))

                    }
                }*/

            } ?: kotlin.run {

                EmptyWeatherBox() {

                    viewModel.onEvent(HomeEvent.OnRetry)
                }
            }


        PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(
                        Alignment.TopCenter
                )
        )

        }


    }





