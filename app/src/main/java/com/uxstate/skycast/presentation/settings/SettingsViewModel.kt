package com.uxstate.skycast.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefs: DataStoreOperations) :
    ViewModel(), SettingsActions {


    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    override fun onThemePreferenceClick() {

        _state.update { it.copy(isShowThemeDialog = true) }
    }

    override fun onTempUnitPreferenceClick() {
        _state.update { it.copy(isShowTempUnitDialog = true) }
    }

    override fun onThemeChange(theme: Theme) {
        viewModelScope.launch {

            prefs.updateTheme(theme)
        }

        _state.update { it.copy(isShowThemeDialog = false) }
    }

    override fun onTempUnitChange(tempUnit: TempUnit) {
    viewModelScope.launch {

        prefs.updateTempUnit(tempUnit)
    }

        _state.update { it.copy(isShowTempUnitDialog = false) }
    }

    override fun onDismissThemeSelectionDialog() {
       _state.update { it.copy(isShowThemeDialog = false) }
    }

    override fun onDismissTempUnitSelectionDialog() {
        _state.update { it.copy(isShowTempUnitDialog = false) }
    }
}