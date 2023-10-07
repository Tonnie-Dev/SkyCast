package com.uxstate.skycast.domain.prefs

import androidx.annotation.StringRes
import com.uxstate.skycast.R

enum class Theme(@StringRes val themeName: Int) {

    SYSTEM(R.string.system_default),
    LIGHT(R.string.light),
    DARK(R.string.dark)
}

enum class TempUnit(@StringRes val tempUnit: Int) {

    FAHRENHEIT(R.string.unit_fahrenheit),
    CELSIUS(R.string.unit_celsius)


}

data class AppPreferences(
    val theme: Theme,
    val tempUnit: TempUnit,
    val savedCityId: Int
)