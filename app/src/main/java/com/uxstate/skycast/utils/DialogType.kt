package com.uxstate.skycast.utils

import androidx.annotation.StringRes
import com.uxstate.skycast.R

enum class DialogType(@StringRes val dialogTitle: Int, @StringRes val dialogText: Int) {

    PERMISSION(R.string.perm_dialog_title, R.string.perm_dialog_text),
    LOCATION(R.string.location_dialog_title, R.string.location_dialog_text),
}