package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.uxstate.skycast.ui.theme.LocalSpacing
import java.util.regex.Pattern

@Composable
fun TrapizForecastItem(modifier: Modifier = Modifier) {

    val spacing = LocalSpacing.current
    Box(modifier = Modifier.padding(16.dp)) {

        Column(
                horizontalAlignment = Alignment.Start,

                modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
        ) {
            Text(text = "19", style = MaterialTheme.typography.displayLarge)
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(text = "07:13AM", style = MaterialTheme.typography.bodySmall)
            Text(text = "22 Oct 2023", style = MaterialTheme.typography.bodySmall)
        }

        Column(modifier = Modifier.align(Alignment.BottomEnd)) {
            Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.minimumInteractiveComponentSize()
            )
            Text(text = "Overcast Clounds", style = MaterialTheme.typography.bodySmall)
        }
    }


}


@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun TrapizForecastItemPreviewLight() {
    TrapizForecastItem()
}

class TrapeziumWeatherShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        return Outline.Generic(

                Path().apply { })


    }


    private fun resize(pathData: String, scaleX: Float, scaleY: Float): String {
        val matcher = Pattern.compile("[0-9]+[.]?([0-9]+)?")
                .matcher(pathData) // match the numbers in the path
        val stringBuffer = StringBuffer()
        var count = 0
        while (matcher.find()) {
            val number = matcher.group()
                    .toFloat()
            matcher.appendReplacement(
                    stringBuffer,
                    (if (count % 2 == 0) number * scaleX else number * scaleY).toString()
            ) // replace numbers with scaled numbers
            ++count
        }
        return stringBuffer.toString() // return the scaled path
    }
}