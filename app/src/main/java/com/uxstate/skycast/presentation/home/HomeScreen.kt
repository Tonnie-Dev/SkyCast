package com.uxstate.skycast.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
@OptIn(ExperimentalMaterialApi::class)

fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {

    val state by viewModel.uiState.collectAsState()

    val isLoading = state.isLoading
    val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = viewModel::refreshWeather
    )


    val scrollState = rememberScrollState()
    val tempUnit = state.appPreferences.tempUnit


  state.currentWeather?.let {

        HomeContent(
                modifier = modifier,
                pullRefreshState = pullRefreshState,
                scrollState = scrollState,
                isLoading = isLoading,
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

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier,
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