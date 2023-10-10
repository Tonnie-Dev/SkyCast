package com.uxstate.skycast.presentation.home

import android.Manifest

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.model.WeatherType
import com.uxstate.skycast.presentation.home.components.HomeBody
import com.uxstate.skycast.presentation.home.components.WeatherDataDisplay
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.CELSIUS_SIGN
import com.uxstate.skycast.utils.FAHRENHEIT
import com.uxstate.skycast.utils.FAHRENHEIT_SIGN
import com.uxstate.skycast.utils.toCelsius
import com.uxstate.skycast.utils.toDateFormat
import com.uxstate.skycast.utils.toFahrenheit


@RootNavGraph(start = true)
@Destination
@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)

fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {


    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)


    val state by viewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = viewModel::refreshWeather
    )


    val scrollState = rememberScrollState()
    val tempUnit = state.appPreferences.tempUnit
    if (permissionState.status.isGranted) {
        state.currentWeather?.let {

            HomeContent(
                    pullRefreshState = pullRefreshState,
                    scrollState = scrollState,
                    isLoading = state.isLoading,
                    location = it.cityName,
                    lastFetchTime = it.lastFetchedTime.toDateFormat(),
                    weatherType = it.networkWeatherDescription.first().description,
                    humidity = it.networkWeatherCondition.humidity,
                    pressure = it.networkWeatherCondition.pressure,
                    windSpeed = it.wind.speed,
                    icon = WeatherType.fromWMO(it.networkWeatherDescription.first().icon).icon,
                    temperature = if (tempUnit.toString() == FAHRENHEIT)
                        "${(it.networkWeatherCondition.temp.toFahrenheit())}${FAHRENHEIT_SIGN}"
                    else
                        "${it.networkWeatherCondition.temp.toCelsius()}${CELSIUS_SIGN}"


            )
        }
    } else {
        Box(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                contentAlignment = Alignment.Center
        ) {
            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                        painter = painterResource(id = R.drawable.ic_no_weather_info),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )

                Text(
                        text = stringResource(id = R.string.location_permission_msg),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                )

                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        }
    }



}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pullRefreshState: PullRefreshState,
    scrollState: ScrollState,
    isLoading: Boolean,
    location: String,
    lastFetchTime: String,
    temperature: String,
    weatherType: String,
    humidity: Double,
    pressure: Double,
    windSpeed: Double,
    @DrawableRes icon: Int
) {
    val spacing = LocalSpacing.current

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {

        Column(
                modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HomeBody(
                    location = location,
                    lastFetchTime = lastFetchTime,
                    temperature = temperature,
                    weatherType = weatherType,
                    icon = icon,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = spacing.spaceSmall,
                                    start = spacing.spaceSmall,
                                    end = spacing.spaceSmall
                            )
            )

            Spacer(modifier = Modifier.height(spacing.spaceOneHundredDp))

            WeatherDataDisplay(
                    modifier = Modifier.paddingFromBaseline(top = spacing.spaceExtraLarge),
                    humidity = humidity,
                    pressure = pressure,
                    windSpeed = windSpeed
            )
        }

        PullRefreshIndicator(
                refreshing = isLoading, state = pullRefreshState, modifier = Modifier.align(
                Alignment.TopCenter
        )
        )
    }
}