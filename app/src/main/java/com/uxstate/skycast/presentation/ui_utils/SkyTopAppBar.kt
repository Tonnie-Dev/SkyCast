package com.uxstate.skycast.presentation.ui_utils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkyTopAppBar(
    title: String,
    isShowActionIcon: Boolean,
    onNavigateBack: () -> Unit,
    onActionIconClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
            modifier = modifier,
            title = { Text(text = title) },
            navigationIcon = {
                Icon(
                        modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clickable { onNavigateBack() },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_text)
                )
            },

            actions = {

                if (isShowActionIcon) {

                    Icon(
                            modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .clickable { onActionIconClick() },
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(
                                    id = R.string.settings

                            )
                    )
                }

            })

}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SkyTopAppBarPreviewLight() {
    SkyTopAppBar(
            title = stringResource(id = R.string.settings),
            isShowActionIcon = true,
            onNavigateBack = { /*TODO*/ },
            onActionIconClick = { /*TODO*/ })
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SkyTopAppBarPreviewDark() {
    SkyTopAppBar(
            title = stringResource(id = R.string.settings),
            isShowActionIcon = true,
            onNavigateBack = { /*TODO*/ },
            onActionIconClick = { /*TODO*/ })
}