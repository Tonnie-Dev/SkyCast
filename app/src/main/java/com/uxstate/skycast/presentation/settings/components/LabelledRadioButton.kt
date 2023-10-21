package com.uxstate.skycast.presentation.settings.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme

@Composable
fun LabelledRadioButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current


    Row(
            modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = text)
    }
}




@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun LabelledRadioButtonPreviewLight() {

    SkyCastTheme {


        Column {
            LabelledRadioButton(text = "Tonnie", isSelected = true, onClick = { /*TODO*/ })
            LabelledRadioButton(text = "Uncle", isSelected = false, onClick = { /*TODO*/ })
        }
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LabelledRadioButtonPreviewDark() {

    SkyCastTheme {


        Column {


                LabelledRadioButton(text = "Tonnie", isSelected = true, onClick = { /*TODO*/ })
                LabelledRadioButton(text = "Uncle", isSelected = false, onClick = { /*TODO*/ })
            }
        }
    }
