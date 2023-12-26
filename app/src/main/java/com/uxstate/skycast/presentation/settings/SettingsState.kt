package com.uxstate.skycast.presentation.settings

import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class SettingsState(
    val appPreferences: AppPreferences = initialAppPreferences,
    val isShowThemeDialog: Boolean = false,
    val isShowTempUnitDialog: Boolean = false
) {


    companion object {

        val initialAppPreferences = AppPreferences(
                theme = Theme.SYSTEM,
                tempUnit = TempUnit.CELSIUS,
                savedCityId = -1
        )

    }
}