package com.uxstate.skycast.presentation.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.presentation.forecast.components.ForecastContent

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun ForecastScreen(navigator: DestinationsNavigator, viewModel:ForecastViewModel= hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    ForecastContent(

           forecastState = state,
            refreshWeatherForecast = viewModel::refreshForecastWeather
    )
}