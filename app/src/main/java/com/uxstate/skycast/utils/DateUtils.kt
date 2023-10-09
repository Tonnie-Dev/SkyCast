package com.uxstate.skycast.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Revisit this 
@SuppressLint("SimpleDateFormat")
fun Long.toDateFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
    return dateFormat.format(date)
}