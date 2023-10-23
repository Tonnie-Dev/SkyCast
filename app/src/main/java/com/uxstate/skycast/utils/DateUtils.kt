package com.uxstate.skycast.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import com.uxstate.skycast.domain.model.ForecastWeather
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
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

fun String.toDateFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEEE MMM d, hh:mm a", Locale.ENGLISH)
    val inputDate = inputFormat.parse(this)
    return outputFormat.format(inputDate)
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


fun List<ForecastWeather>.mapForecastWeather(selectedDay:Int):List<ForecastWeather>? {

    val mappedForecastByDate = this.groupBy { it.date.substring(0..9) }
    val dateKeys = mappedForecastByDate.keys.toList()
    Timber.i("The length is ${dateKeys.size}")
    val index = dateKeys[selectedDay]

  // check(selectedDay in 0..4){"Invalid Day Selection"}

    return mappedForecastByDate[index]
}

fun String.extractTime(): String {

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime =  LocalDateTime.parse(this, dateTimeFormatter)

    val hour = localDateTime.hour
    val minute = localDateTime.minute

    val formattedHour = if(hour in 0..9) "0$hour" else hour
    val formattedMinute = if(minute in 0..9) "0$minute" else minute

   // return "$formattedHour:$formattedMinute"
    return  localDateTime.extractAmPmTime()
}

fun LocalDateTime.extractAmPmTime():String {

    val pattern = "hh:mm a"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return  format(dateTimeFormatter)
}

