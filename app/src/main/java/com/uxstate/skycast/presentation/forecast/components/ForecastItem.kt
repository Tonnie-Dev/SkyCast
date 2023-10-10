package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing

@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    dateTime: String,
    weatherType: String,
    temperature: String,
    @DrawableRes icon: Int
) {

    val spacing = LocalSpacing.current

    Row(
            modifier = modifier
                    .animateContentSize()
                    .padding(horizontal = spacing.spaceSmall)
                    .clip(RoundedCornerShape(spacing.spaceLarge))
                    .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
Column (modifier = Modifier.padding(spacing.spaceMedium)){

    Text(
            text = temperature,
            style = MaterialTheme.typography.titleLarge,
    )

    Text(
            modifier = Modifier.padding(top = spacing.spaceExtraSmall),
            text = weatherType,
            style = MaterialTheme.typography.bodyMedium,
    )

    Text(
            modifier = Modifier.padding(top = spacing.spaceMedium),
            text = dateTime,
            style = MaterialTheme.typography.bodySmall,
    )
    
}

        Column(
                modifier = Modifier.padding(spacing.spaceMedium),
                horizontalAlignment = Alignment.End
        ) {
            Image(
                    modifier = Modifier
                            .size(spacing.spaceExtraLarge),
                    colorFilter = ColorFilter.tint(Color.White),
                    painter = painterResource(id = icon),
                    contentDescription = null,
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun ForecastItemPreviewLight() {
    ForecastItem(
            dateTime = "8 Apr 2023, 06:00AM",
            weatherType = "Cloudy",
            temperature = "13.55",
            icon = R.drawable.ic_snowy
    )
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ForecastItemPreviewDark() {
    ForecastItem(
            dateTime = "9 Jun 2023, 06:00AM",
            weatherType = "Overcast",
            temperature = "20.45",
            icon = R.drawable.ic_cloudy
    )
}