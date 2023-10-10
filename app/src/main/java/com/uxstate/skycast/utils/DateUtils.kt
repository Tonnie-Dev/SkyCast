package com.uxstate.skycast.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
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

@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}