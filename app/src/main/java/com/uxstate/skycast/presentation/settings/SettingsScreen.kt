package com.uxstate.skycast.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.presentation.settings.components.SettingsContent
import com.uxstate.skycast.presentation.settings.components.SingleChoiceDialog

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val currentTheme = state.appPreferences.theme
    val currentTempUnit = state.appPreferences.tempUnit



    SettingsContent(appPreferences = state.appPreferences, actions = viewModel)

    if (state.isShowThemeDialog) {

        SingleChoiceDialog(
                title = stringResource(id = R.string.theme),
                options = Theme.values()
                        .toList()
                        .map { stringResource(id = it.themeName) },
                initialSelectedOptionIndex = Theme.values()
                        .indexOf(currentTheme),
                onConfirmOption = { viewModel.onThemeChange(Theme.values()[it]) }
        ) {
            viewModel.onDismissThemeSelectionDialog()
        }
    }

    if (state.isShowTempUnitDialog) {


        SingleChoiceDialog(
                title = stringResource(id = R.string.temperature_unit),
                options = TempUnit.values()
                        .toList()
                        .map { stringResource(id = it.unit) },
                initialSelectedOptionIndex = TempUnit.values()
                        .indexOf(currentTempUnit),
                onConfirmOption = { viewModel.onTempUnitChange(TempUnit.values()[it]) }
        ) {
            viewModel.onDismissTempUnitSelectionDialog()
        }
    }

}