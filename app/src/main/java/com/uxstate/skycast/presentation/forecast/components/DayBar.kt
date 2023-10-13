package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme
import com.uxstate.skycast.utils.conditional

@Composable
fun DayBar(
    isToday: Boolean,
    isSelected: Boolean,
    day: String,
    dayOfWeek: String,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    val dayOfWeekText = if (isToday) stringResource(id = R.string.today_text) else dayOfWeek
    Surface(modifier = modifier) {
        Column(
                modifier = Modifier
                        .conditional(isSelected) {
                            Modifier.padding(
                                    bottom = spacing.spaceExtraSmall,
                                    top = spacing.spaceSmall,
                                    end = spacing.spaceSmall,
                                    start = spacing.spaceSmall
                            )
                        }
                        .conditional(!isSelected) {

                            Modifier.padding(
                                    bottom = spacing.spaceSmall + spacing.spaceExtraSmall,
                                    top = spacing.spaceSmall,
                                    end = spacing.spaceSmall,
                                    start = spacing.spaceSmall
                            )
                        },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
        ) {

            Text(
                    text = dayOfWeekText,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.primary
            )
            Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary
            )


            AnimatedVisibility(visible = isSelected) {
                Surface(
                        shape = RectangleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                                .width(spacing.spaceLarge + spacing.spaceSmall)
                                .height(spacing.spaceExtraSmall)
                ) {}
            }

        }
    }


}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun DayOfWeekPreviewLight() {

    SkyCastTheme {
        Row() {
            DayBar(isToday = true, isSelected = true, day = "04 OCT", dayOfWeek = "Fri")
            DayBar(isToday = false, isSelected = false, day = "13 OCT", dayOfWeek = "Sat")
            DayBar(isToday = false, isSelected = false, day = "14 OCT", dayOfWeek = "Sun")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DayOfWeekPreviewDark() {

    SkyCastTheme {
        Row() {
            DayBar(isToday = true, isSelected = true, day = "09 OCT", dayOfWeek = "Fri")
            DayBar(isToday = false, isSelected = false, day = "10 OCT", dayOfWeek = "Sat")
            DayBar(isToday = false, isSelected = true, day = "15 OCT", dayOfWeek = "Sun")
        }

    }
}