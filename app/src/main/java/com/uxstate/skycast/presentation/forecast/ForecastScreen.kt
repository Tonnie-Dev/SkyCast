package com.uxstate.skycast.presentation.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.presentation.forecast.components.DateRow
import com.uxstate.skycast.presentation.forecast.components.ForecastContent


@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun ForecastScreen(navigator: DestinationsNavigator, viewModel:ForecastViewModel= hiltViewModel()) {
    val state by viewModel.state.collectAsState()
val selectedDay = state.selectedDay

    Column ( modifier = Modifier.statusBarsPadding().navigationBarsPadding()){
        DateRow() {

            viewModel.onEvent(ForecastEvent.OnDateChangeEvent(it))
        }
        ForecastContent(
                state = state,
                refreshWeatherForecast = viewModel::refreshForecastWeather
        )
    }

}