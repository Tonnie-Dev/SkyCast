package com.uxstate.skycast.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.WeatherType
import com.uxstate.skycast.presentation.destinations.ForecastScreenDestination
import com.uxstate.skycast.presentation.destinations.SettingsScreenDestination
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.CELSIUS_SIGN
import com.uxstate.skycast.utils.FAHRENHEIT
import com.uxstate.skycast.utils.FAHRENHEIT_SIGN
import com.uxstate.skycast.utils.roundOffDoubleToInt
import com.uxstate.skycast.utils.toCelsius
import com.uxstate.skycast.utils.toDateFormat
import com.uxstate.skycast.utils.toFahrenheit
import com.uxstate.skycast.utils.toTitleCase

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    isFahrenheitUnit: Boolean,
    currentWeather: CurrentWeather,
    isLoading: Boolean,
    @DrawableRes icon: Int,
    onForecastButtonClick: () -> Unit,
    navigateToSettings: () -> Unit,
    onRefreshWeather: () -> Unit
) {
    val spacing = LocalSpacing.current
    val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = onRefreshWeather
    )

    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
    ) {
    Column(
            modifier = modifier.verticalScroll(rememberScrollState())
            ,
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
                cityName = currentWeather.cityName,
                lastFetchTime = currentWeather.lastFetchedTime.toDateFormat(),
                temperature = if (isFahrenheitUnit)
                    "${
                        (currentWeather.networkWeatherCondition.temp.toFahrenheit()
                                .roundOffDoubleToInt())
                    }$FAHRENHEIT_SIGN"
                else
                    "${
                        currentWeather.networkWeatherCondition.temp.toCelsius()
                                .roundOffDoubleToInt()
                    }$CELSIUS_SIGN",
                weatherType = currentWeather.networkWeatherDescription.first().description.toTitleCase(),
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
                humidity = currentWeather.networkWeatherCondition.humidity,
                pressure = currentWeather.networkWeatherCondition.pressure,
                windSpeed = currentWeather.wind.speed
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        Button(onClick = onForecastButtonClick) {

            Text(stringResource(R.string.forecast_text))
        }
    }

    PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(
                    Alignment.TopCenter
            )
    )
}


}


@Composable
fun LoadHomeContent(viewModel:HomeViewModel, navigator:DestinationsNavigator) {


    val state by viewModel.state.collectAsState()
    val isLoading = state.isLoading
    val isFahrenheitUnit = state.appPreferences.tempUnit.toString() == FAHRENHEIT


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
    }

}
