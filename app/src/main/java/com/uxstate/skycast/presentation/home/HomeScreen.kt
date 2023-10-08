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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uxstate.skycast.presentation.home.components.HomeBody
import com.uxstate.skycast.presentation.home.components.WeatherDataDisplay
import com.uxstate.skycast.ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(modifier: Modifier, uiState: HomeState, refreshWeather: () -> Unit) {


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