package com.uxstate.skycast.presentation.settings

import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

interface SettingsActions {

    fun onThemePreferenceClick()
    fun onTempUnitPreferenceClick()
    fun onThemeChange(theme: Theme)
    fun onTempUnitChange(tempUnit: TempUnit)
    fun onDismissThemeSelectionDialog()
    fun onDismissTempUnitSelectionDialog()
}