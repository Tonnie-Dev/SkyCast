package com.uxstate.skycast.presentation.settings

interface SettingsScreenActions {

    fun onThemePreferenceClick()
    fun onTempUnitPreferenceClick()
    fun onThemeChange()
    fun onTempUnitChange()
    fun onDismissThemeSelectionDialog()
    fun onDismissTempUnitSelectionDialog()
}