package com.uxstate.skycast.presentation.forecast.components

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
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.model.WeatherType
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.CELSIUS_SIGN
import com.uxstate.skycast.utils.FAHRENHEIT
import com.uxstate.skycast.utils.FAHRENHEIT_SIGN
import com.uxstate.skycast.utils.displayText
import com.uxstate.skycast.utils.filterForecastWeatherByDay
import com.uxstate.skycast.utils.getDifferences
import com.uxstate.skycast.utils.toCelsius
import com.uxstate.skycast.utils.toDateFormat
import com.uxstate.skycast.utils.toFahrenheit

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun ForecastContent(
    modifier: Modifier = Modifier,
    forecastState: ForecastState,
    refreshWeatherForecast: () -> Unit
) {
    val spacing = LocalSpacing.current
    val isForecastLoading = forecastState.isLoading
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isForecastLoading, { refreshWeatherForecast() })
    val selectedTempUnit = forecastState.prefs.tempUnit.toString()
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate }
    val endDate = remember { currentDate.plusDays(7) }
    val selection = remember { mutableStateOf(currentDate) }
    val weatherState = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
    )
    val dayNo = selection.getDifferences(LocalDate.now())

    Box(
            modifier = modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxWidth()
    ) {
        Column(
                horizontalAlignment = CenterHorizontally
        ) {
            WeekCalendar(
                    state = weatherState,
                    dayContent = { day ->
                        Day(day.date, isSelected = selection.value == day.date) { clicked ->
                            if (selection.value != clicked) {
                                selection.value = clicked
                            }
                        }
                    },
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium + spacing.spaceSmall))

           forecastState.forecastData?.let {


                if (dayNo in 0..5) {
                    it.filterForecastWeatherByDay(dayNo)
                            .let { filteredList ->


                                LazyColumn(
                                        contentPadding = PaddingValues(bottom = 16.dp)
                                ) {
                                    itemsIndexed(filteredList) { index, item ->
                                        if (index != 0) {
                                            Spacer(modifier = Modifier.height(16.dp))
                                        }

                                        item.forecastWeatherDescription.forEach { description ->
                                            ForecastItem(
                                                    dateTime = item.date.toDateFormat(),
                                                    weatherType = description.description.toString(),
                                                    temperature =
                                                    if (selectedTempUnit == FAHRENHEIT) "${
                                                        (item.forecastWeatherParams.temp.toFahrenheit())
                                                    }${FAHRENHEIT_SIGN}" else "${item.forecastWeatherParams.temp.toCelsius()}${CELSIUS_SIGN}",
                                                   icon = WeatherType.fromWMO(description.icon.toString()).icon
                                            )
                                            Spacer(modifier = Modifier.height(spacing.spaceSmall))
                                        }
                                    }
                                }
                            }
                } else {
                    Box(
                            modifier = modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                            contentAlignment = Alignment.Center
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
                                    style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }}}





@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (date: LocalDate) -> Unit) {

    val spacing = LocalSpacing.current

    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { onClick(date) }, contentAlignment = Alignment.Center)
    {


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
                    modifier = Modifier
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