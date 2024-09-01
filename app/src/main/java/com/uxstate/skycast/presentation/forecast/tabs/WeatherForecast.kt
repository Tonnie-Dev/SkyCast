package com.uxstate.skycast.presentation.forecast.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.model.WeatherType
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.presentation.forecast.components.TrapizForecastItem
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.CELSIUS_SIGN
import com.uxstate.skycast.utils.FAHRENHEIT
import com.uxstate.skycast.utils.FAHRENHEIT_SIGN
import com.uxstate.skycast.utils.displayText
import com.uxstate.skycast.utils.mapForecastWeather
import com.uxstate.skycast.utils.roundOffDoubleToInt
import com.uxstate.skycast.utils.toCelsius
import com.uxstate.skycast.utils.toFahrenheit
import com.uxstate.skycast.utils.toTitleCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun WeatherForecast(
    modifier: Modifier = Modifier,
    state: ForecastState,
    page: Int,
    onRefreshForecast: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val isForecastLoading = state.isLoading
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isForecastLoading, { onRefreshForecast() })
    val selectedTempUnit = state.appPreferences.tempUnit.toString()

    Box(
        modifier =
            modifier
                .pullRefresh(pullRefreshState)
                .fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
        ) {
            if (state.forecastData.isNotEmpty()) {
                state.forecastData
                    .mapForecastWeather(page)
                    ?.let { filteredList ->

                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            itemsIndexed(filteredList) { index, item ->
                                if (index != 0) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }

                                item.forecastWeatherDescription.forEach { description ->
                                    TrapizForecastItem(
                                        dateTime = item.date,
                                        weatherDesc = description.description.toTitleCase(),
                                        temp =
                                            if (selectedTempUnit == FAHRENHEIT) {
                                                "${
                                                    (
                                                        item.forecastWeatherParams.temp
                                                            .toFahrenheit()
                                                            .roundOffDoubleToInt()
                                                    )
                                                }${FAHRENHEIT_SIGN}"
                                            } else {
                                                "${
                                                    item.forecastWeatherParams.temp.toCelsius()
                                                        .roundOffDoubleToInt()
                                                }${CELSIUS_SIGN}"
                                            },
                                        icon = WeatherType.fromWMO(description.icon).icon,
                                    )
                                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                                }
                            }
                        }
                    } ?: run {
                    Box(
                        modifier =
                            modifier
                                .fillMaxSize()
                                .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_no_weather_info),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            )

                            Text(
                                text = stringResource(id = R.string.future_weather_msg),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(
    date: LocalDate,
    isSelected: Boolean,
    onClick: (date: LocalDate) -> Unit,
) {
    val spacing = LocalSpacing.current

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = spacing.spaceSmall),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
        ) {
            Text(
                text = date.dayOfWeek.displayText(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(spacing.spaceSmall)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("dd")
