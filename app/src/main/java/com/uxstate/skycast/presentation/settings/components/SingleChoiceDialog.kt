package com.uxstate.skycast.presentation.settings.components


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme


@Composable
fun SingleChoiceDialog(
    title: String,
    options: List<String>,
    initialSelectedOptionIndex: Int,
    onConfirmOption: (index: Int) -> Unit,
    onDismiss: () -> Unit
) {

    val spacing = LocalSpacing.current
    var selectedOptionIndex by rememberSaveable {
        mutableIntStateOf(initialSelectedOptionIndex)
    }

    AlertDialog(

            onDismissRequest = onDismiss,
            modifier = Modifier.clip(RoundedCornerShape(spacing.spaceMedium)),
            title = { Text(title) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmOption(selectedOptionIndex)
                })
                {
                    Text(text = stringResource(id = R.string.ok_text))
                }
            },
            text = {

                Column {
                    options.forEachIndexed { i, option ->


                        LabelledRadioButton(
                                text = option,
                                isSelected = selectedOptionIndex == i,
                                onClick = { selectedOptionIndex = i })
                    }
                }


            }
    )

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SingleChoiceDialogPreviewLight() {

    SkyCastTheme {

        SingleChoiceDialog(
                title = "Theme",
                options = listOf("Dark", "Light", "System"),
                initialSelectedOptionIndex = 0,
                onConfirmOption = {}
        ) {

        }
    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SingleChoiceDialogPreviewDark() {

    SkyCastTheme {

        SingleChoiceDialog(
                title = "Theme",
                options = listOf("Dark", "Light", "System"),
                initialSelectedOptionIndex = 0,
                onConfirmOption = {}
        ) {

        }
    }

}