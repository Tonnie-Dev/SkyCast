package com.uxstate.skycast.presentation.settings

import androidx.lifecycle.ViewModel
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefs: DataStoreOperations) :
    ViewModel(), SettingsScreenActions {


    

    override fun onThemePreferenceClick() {
        TODO("Not yet implemented")
    }

    override fun onTempUnitPreferenceClick() {
        TODO("Not yet implemented")
    }

    override fun onThemeChange() {
        TODO("Not yet implemented")
    }

    override fun onTempUnitChange() {
        TODO("Not yet implemented")
    }

    override fun onDismissThemeSelectionDialog() {
        TODO("Not yet implemented")
    }

    override fun onDismissTempUnitSelectionDialog() {
        TODO("Not yet implemented")
    }
}