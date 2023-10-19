package com.uxstate.skycast.presentation.settings

import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class SettingsScreenState(
    val appPreferences: AppPreferences,
    val showThemeDialog: Boolean,
    val showTempUnitDialog: Boolean
) {


    companion object {

        val initialSettingsState = SettingsScreenState(
                appPreferences = AppPreferences(
                        theme = Theme.SYSTEM,
                        tempUnit = TempUnit.CELSIUS,
                        savedCityId = -1
                ), showThemeDialog = false, showTempUnitDialog = false
        )
    }
}