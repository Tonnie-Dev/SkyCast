package com.uxstate.skycast.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

// TODO: Revisit this
@SuppressLint("SimpleDateFormat")
fun Long.toDateFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
    return dateFormat.format(date)
}

// TODO: Revisit this
@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.displayText(uppercase: Boolean = false): String =
    getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }

fun String.extractTime(): String {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(this, dateTimeFormatter)

    val hour = localDateTime.hour
    val minute = localDateTime.minute

    val formattedHour = if (hour in 0..9) "0$hour" else hour
    val formattedMinute = if (minute in 0..9) "0$minute" else minute

    return localDateTime.extractAmPmTime()
}

fun LocalDateTime.extractAmPmTime(): String {
    val pattern = "hh:mm a"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    val formattedTime = this.format(dateTimeFormatter)
    val amPmSubstring =
        formattedTime
            .substring(6..7)
            .uppercase()
    return formattedTime.substring(0..5) + amPmSubstring
}

fun shortDayOfWeek(daysToAdd: Long = 0L): String {
    val day =
        LocalDate
            .now()
            .plusDays(daysToAdd)
            .dayOfWeek
    return if (day == LocalDate.now().dayOfWeek) "Today" else day.name.substring(0..2)
}

fun shortDate(daysToAdd: Long = 0L): String {
    val day =
        LocalDate
            .now()
            .plusDays(daysToAdd)
            .dayOfMonth
    val prefixedDay = if (day in 1..9) "0$day" else "$day"

    return prefixedDay.let {
        it
            .plus(EMPTY_STRING)
            .plus(
                LocalDate
                    .now()
                    .plusDays(daysToAdd)
                    .month
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
    }
}
