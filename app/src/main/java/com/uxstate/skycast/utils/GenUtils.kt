package com.uxstate.skycast.utils

import androidx.compose.ui.Modifier

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {

    return if (condition)
        then(modifier())
    else
        this
}