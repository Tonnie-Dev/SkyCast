package com.uxstate.skycast.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmptyWeatherBox( onGetLocation:()-> Unit) {

val spacing = LocalSpacing.current
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.spaceSmall),
            contentAlignment = Alignment.Center
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                    painter = painterResource(id = R.drawable.ic_no_weather_info),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            )

            Text(
                    text = stringResource(id = R.string.location_permission_msg),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
            )

            Button(onClick = { onGetLocation() }) {
                Text("Try Again")
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun EmptyWeatherBoxPreviewLight() {

    SkyCastTheme {

        EmptyWeatherBox(){}
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyWeatherBoxPreviewDark() {

    SkyCastTheme {

        EmptyWeatherBox(){}
    }
}