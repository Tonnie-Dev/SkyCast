package com.uxstate.skycast.presentation.ui_utils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.ui.theme.SkyCastTheme

@Composable
fun SkyButton(
    modifier: Modifier = Modifier,
    hasIcon: Boolean = false,
    text: String,
    onClickButton: () -> Unit,
    @DrawableRes icon: Int = 0,
    color: Color = MaterialTheme.colorScheme.primary
) {

    val spacing = LocalSpacing.current
    Button(
            onClick = onClickButton,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {

        Row {
            if (hasIcon && icon != 0) {
                Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))
            }

            Text(text = text)

        }
    }

}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SkyButtonPreviewLight() {

    SkyCastTheme {

        Column {
            SkyButton(hasIcon = true, text = "Press Me", icon = R.drawable.bug, onClickButton = { })
            SkyButton(hasIcon = false, text = "Press Me", icon = R.drawable.bug, onClickButton = { })
            SkyButton(
                    hasIcon = false,
                    text = "Press Me",
                    icon = R.drawable.bug,
                    color = MaterialTheme.colorScheme.error,
                    onClickButton = { })
        }

    }



}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SkyButtonPreviewDark() {

    SkyCastTheme {

        Column {
            SkyButton(hasIcon = true, text = "Press Me", icon = R.drawable.bug, onClickButton = { })
            SkyButton(hasIcon = false, text = "Press Me", icon = R.drawable.bug, onClickButton = { })
            SkyButton(
                    hasIcon = false,
                    text = "Press Me",
                    icon = R.drawable.bug,
                    color = MaterialTheme.colorScheme.error,
                    onClickButton = { })
        }


    }



}