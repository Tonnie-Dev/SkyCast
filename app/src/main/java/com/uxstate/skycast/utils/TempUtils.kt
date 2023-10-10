package com.uxstate.skycast.utils

import java.text.DecimalFormat

// TODO: Revisit these two extension functions
fun Double.toFahrenheit(): Double {
    return DecimalFormat().run {
        applyPattern(".##")
        this.parse(format((this@toFahrenheit - 273.15).times(1.8).plus(32)))!!.toDouble()
    }
}

fun Double.toCelsius(): Double {
    return DecimalFormat().run {
        applyPattern(".##")
        this.parse(format(this@toCelsius - 273.15))!!.toDouble()
    }
}