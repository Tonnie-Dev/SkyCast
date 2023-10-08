package com.uxstate.skycast.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.R

@Composable
fun WeatherDataDisplay(modifier: Modifier, humidity: Double, pressure: Double, windSpeed: Double) {

    val spacing = LocalSpacing.current
    Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {

    }
}

@Composable
fun WeatherDataElement(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes text: Int,
    value: String
) {
    val spacing = LocalSpacing.current

    Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.primary
            )
    )

    Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.paddingFromBaseline(
                    top = spacing.spaceSmall,
                    bottom = spacing.spaceSmall
            )

    )

    Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.paddingFromBaseline(
                    top = spacing.spaceSmall,
                    bottom = spacing.spaceSmall
            )
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WeatherDataElementPreviewDark() {

    SkyCastTheme {

        WeatherDataElement(icon = R.drawable.ic_speed, text = R.string.h, value ="33.0%" )
    }
}