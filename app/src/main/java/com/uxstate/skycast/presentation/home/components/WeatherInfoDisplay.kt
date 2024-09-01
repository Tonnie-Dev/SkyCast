package com.uxstate.skycast.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    cityName: String,
    lastFetchTime: String,
    temperature: String,
    weatherType: String,
    @DrawableRes icon: Int,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = cityName, style = MaterialTheme.typography.headlineLarge)

        Text(
            text = lastFetchTime,
            style = MaterialTheme.typography.titleMedium,
            modifier =
                Modifier.padding(
                    top = spacing.spaceMedium + spacing.spaceSmall,
                    bottom = spacing.spaceSmall,
                ),
        )

        // TODO: research color filter
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(spacing.spaceOneHundred),
        )
        Text(text = temperature, style = MaterialTheme.typography.titleLarge)

        // TODO: Research on paddingFromBaseLine
        Text(
            text = weatherType,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.paddingFromBaseline(top = spacing.spaceMedium + spacing.spaceSmall),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeHeaderPreviewDark() {
    HomeBody(
        cityName = "Riruta",
        lastFetchTime = "Sunday, Oct 9, 07:13 AM",
        temperature = "24.2",
        weatherType = "Clear Sky",
        icon = R.drawable.ic_clear_sky,
        modifier = Modifier.fillMaxWidth(),
    )
}
