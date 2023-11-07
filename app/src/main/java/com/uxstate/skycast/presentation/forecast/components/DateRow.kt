package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme

@Composable
fun DateRow(modifier: Modifier = Modifier, onSelectDate: (Int) -> Unit) {

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
    ) {

        for (i in 0..4) {

            val isSelected = (selectedIndex == i)
            DateTab(index = i, isSelected = isSelected,
                    onClickDateTab = {
                        selectedIndex = i
                        onSelectDate(i)
                    })

        }
    }

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DateRowPreviewLight() {

    SkyCastTheme {
        DateRow {

        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DateRowPreviewDark() {

    SkyCastTheme {
        DateRow {

        }
    }
}