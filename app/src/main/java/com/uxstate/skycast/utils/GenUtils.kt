package com.uxstate.skycast.utils

import androidx.compose.ui.Modifier


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {

    return if (condition)
        then(modifier())
    else
        this
}


fun Double.roundOffDoubleToInt():Int  {

    val intValue = this.toInt()
    val fractionValue = this - intValue

    return if (fractionValue >= 0.5) intValue + 1 else intValue

}

fun String.toTitleCase(delimiter:String = " "):String{

    return split(delimiter).joinToString (delimiter){
         word ->

        val smallCaseWord = word.lowercase()

        smallCaseWord.replaceFirstChar (Char::titlecase)
    }
}
