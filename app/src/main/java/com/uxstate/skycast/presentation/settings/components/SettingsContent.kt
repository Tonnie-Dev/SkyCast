package com.uxstate.skycast.presentation.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.uxstate.skycast.ui.theme.LocalSpacing

@Composable
fun SettingsContent(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val spacing = LocalSpacing.current

    Column(modifier = modifier) {
        Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.paddingFromBaseline(
                        top = spacing.spaceLarge,
                        bottom = spacing.spaceMedium
                ).padding(spacing.spaceMedium)
        )
    }

    content()
}