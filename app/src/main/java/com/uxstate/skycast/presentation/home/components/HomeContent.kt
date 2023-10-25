package com.uxstate.skycast.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing

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
    @DrawableRes icon: Int,
    onForecastButtonClick: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val spacing = LocalSpacing.current

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {

        Column(
                modifier = Modifier
                        .verticalScroll(scrollState)
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.spaceSmall), Arrangement.End
            ) {
                IconButton(onClick = navigateToSettings) {

                    Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.settings),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.minimumInteractiveComponentSize()
                    )
                }
            }
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

            Spacer(modifier = Modifier.height(spacing.spaceOneHundred))

            WeatherDataDisplay(
                    modifier = Modifier.paddingFromBaseline(top = spacing.spaceExtraLarge),
                    humidity = humidity,
                    pressure = pressure,
                    windSpeed = windSpeed
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            Button(onClick = onForecastButtonClick) {

                Text("Forecast")
            }
        }

        PullRefreshIndicator(
                refreshing = isLoading, state = pullRefreshState, modifier = Modifier.align(
                Alignment.TopCenter
        )
        )
    }
}