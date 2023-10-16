package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
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
import java.time.LocalDate


@Composable
fun DateTab(
    index: Int,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClickDateTab: (index:Int) -> Unit
) {

    val spacing = LocalSpacing.current
    val today = LocalDate.now()

    val tabDate = rememberSaveable {

        when (index) {

            0 -> today
            1 -> today.plusDays(1)
            2 -> today.plusDays(2)
            3 -> today.plusDays(3)
            4 -> today.plusDays(4)
            else -> today
        }

    }

    val isToday = (tabDate.dayOfMonth == today.dayOfMonth)

    val dayOfWeek = if (isToday) stringResource(id = R.string.today_text)
    else
        tabDate.dayOfWeek.name.substring(0..2)


    val shortMonth = tabDate.month.name.substring(0..2)
    val dayOfMonth = tabDate.dayOfMonth.toString()
    val displayDate = dayOfMonth.plus(" ")
            .plus(shortMonth)


    Surface(modifier = modifier.clickable{onClickDateTab(index)}.animateContentSize()) {
        Column(
                modifier = Modifier.width(IntrinsicSize.Max)
                        .conditional(isSelected) {
                            Modifier.padding(
                                    bottom = spacing.spaceExtraSmall,
                                    top = spacing.spaceSmall,
                                    end = spacing.spaceMedium,
                                    start = spacing.spaceMedium
                            )
                        }
                        .conditional(!isSelected) {

                            Modifier.padding(
                                    bottom = spacing.spaceSmall + spacing.spaceExtraSmall,
                                    top = spacing.spaceSmall,
                                    end = spacing.spaceMedium,
                                    start = spacing.spaceMedium
                            )
                        },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
        ) {

            Text(
                    text = dayOfWeek,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier

            )
            Text(
                    text = displayDate,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier

            )


          if( isSelected) {
                Surface(
                        shape = RectangleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                                .height(spacing.spaceExtraSmall)
                                .fillMaxWidth()

                ) {}
            }

        }
    }


}


@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun DateTabPreviewLight() {

    SkyCastTheme {
        Row() {
            DateTab(index = 0, isSelected = true, onClickDateTab = {})
            DateTab(index = 1, isSelected = false, onClickDateTab = {})
            DateTab(index = 2, isSelected = false, onClickDateTab = {})

        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DateTabPreviewDark() {

    SkyCastTheme {
        Row() {
            DateTab(index = 0, isSelected = true, onClickDateTab = {})
            DateTab(index = 1, isSelected = false, onClickDateTab = {})
            DateTab(index = 2, isSelected = false, onClickDateTab = {})
        }

    }
}