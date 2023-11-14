package com.uxstate.skycast.presentation.ui_utils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme

@Composable
fun SkyProgressBar() {

    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        LinearProgressIndicator(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceOneHundred), color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ShowLinearLoadingBar() {
    SkyProgressBar()
}
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun LinearProgressBarPreviewLight() {

    SkyCastTheme {

        SkyProgressBar()
    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LinearProgressBarPreviewDark() {

    SkyCastTheme {

        SkyProgressBar()
    }

}