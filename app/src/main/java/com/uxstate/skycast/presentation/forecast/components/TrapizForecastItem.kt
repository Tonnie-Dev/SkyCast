package com.uxstate.skycast.presentation.forecast.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import com.uxstate.skycast.ui.theme.LocalSpacing
import java.util.regex.Pattern
import com.uxstate.skycast.R

@Composable
fun TrapizForecastItem(temp:Int,modifier: Modifier = Modifier) {

    val spacing = LocalSpacing.current
    Box(modifier = Modifier
            .clip(TrapeziumWeatherShape())
            .background(Color.Cyan)
            .padding(16.dp)) {

        Column(
                horizontalAlignment = Alignment.Start,

                modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
        ) {
            Text(text = "0$temp", style = MaterialTheme.typography.displayLarge)
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(text = "07:13AM", style = MaterialTheme.typography.bodySmall)
            Text(text = "22 Oct 2023", style = MaterialTheme.typography.bodySmall)
        }

        Column(modifier = Modifier.align(Alignment.BottomEnd), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                    painter = painterResource(id = R.drawable.ic_cloudy),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(spacing.spaceExtraLarge)
            )
            Text(text = "Overcast Clounds", style = MaterialTheme.typography.bodySmall)
        }
    }


}


@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun TrapizForecastItemPreviewLight() {
  Column {
      repeat(5){i ->

          TrapizForecastItem(i)
          Spacer(modifier = Modifier.height(16.dp))
      }
  }
}

class TrapeziumWeatherShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val scaleX = size.width/340f
        val scaleY = size.height/167f
        val pathData= """M1.13801 66.5329C0.149343 32.5295 -0.344994 15.5279 10.4273 6.20756C21.1996 -3.11273 37.9536 -0.179026 71.4615 5.68838L301.126 45.9037C317.815 48.8261 326.159 50.2872 331.268 56.104C336.377 61.9208 336.75 70.384 337.494 87.3105L338.979 121.066C339.928 142.636 340.403 153.421 333.905 160.211C327.408 167 316.613 167 295.022 167H46.7985C26.6014 167 16.5028 167 10.1002 160.781C3.69751 154.562 3.40401 144.467 2.81702 124.279L1.13801 66.5329Z""".trimIndent()

        return Outline.Generic(PathParser.createPathFromPathData(resize(pathData,scaleX, scaleY)).asComposePath())



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