package com.uxstate.skycast.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import com.uxstate.skycast.domain.model.ForecastWeather
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

// TODO: Revisit this
@SuppressLint("SimpleDateFormat")
fun Long.toDateFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
    return dateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun MutableState<LocalDate>.getDifferences(fromDate: LocalDate): Int {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val from = LocalDate.parse(fromDate.toString(), dateFormatter)
    val to = LocalDate.parse(this.value.toString(), dateFormatter)

    return Period.between(from, to).days
}

fun List<ForecastWeather>.filterForecastWeatherByDay(selectedDay: Int): List<ForecastWeather> {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, selectedDay)
    val checkerDay = calendar.get(Calendar.DATE)
    val checkerMonth = calendar.get(Calendar.MONTH)
    val checkerYear = calendar.get(Calendar.YEAR)

    val filteredList = this.filter { weatherForecast ->
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val formattedDate = format.parse(weatherForecast.date)
        val weatherForecastDay = formattedDate?.date
        val weatherForecastMonth = formattedDate?.month
        val weatherForecastYear = formattedDate?.year

        // This checks if the selected day, month and year are equal. The year requires an addition of 1900 to get the correct year.
        weatherForecastDay == checkerDay && weatherForecastMonth == checkerMonth && weatherForecastYear?.plus(
                1900
        ) == checkerYear
    }

    return filteredList
}


