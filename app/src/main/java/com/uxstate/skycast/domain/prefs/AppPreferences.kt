package com.uxstate.skycast.domain.prefs

import androidx.annotation.StringRes
import com.uxstate.skycast.R

enum class Theme(@StringRes val themeName:Int){

    SYSTEM(R.string.system_default),
    LIGHT(R.string.light),
    DARK(R.string.dark)
}