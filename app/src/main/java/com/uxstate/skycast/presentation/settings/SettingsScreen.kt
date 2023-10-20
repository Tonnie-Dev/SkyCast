package com.uxstate.skycast.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.presentation.settings.components.SettingsContent
import com.uxstate.skycast.presentation.settings.components.SingleChoiceDialog
import com.uxstate.skycast.ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsState()
    val currentTheme = state.appPreferences.theme
    val currentTempUnit = state.appPreferences.tempUnit

    Scaffold(topBar = {

        CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = { Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable{ navigator.navigateUp()}
                )},
                modifier = Modifier.padding(horizontal = spacing.spaceMedium)

        ) }) { paddingValues ->
        SettingsContent(
                appPreferences = state.appPreferences,
                actions = viewModel,
                modifier = Modifier.padding(paddingValues)
        )
    }



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