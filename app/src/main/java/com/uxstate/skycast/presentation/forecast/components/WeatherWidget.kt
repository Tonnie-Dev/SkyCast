package com.uxstate.skycast.presentation.forecast.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.uxstate.skycast.ui.theme.Purple40

@Composable
fun WeatherWidget() {

    Box(
            modifier = Modifier
                    .background(Purple40)
                    .fillMaxSize().clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
    ) {

        Spacer(
                modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(3 / 2f)
                        .fillMaxSize()
                        .drawWithCache
                        {


                            val path = generatePath()

                            onDrawBehind {


                                drawPath(path, Color.Green, style = Stroke(2.dp.toPx()))
                                val strokeWidth = 1.dp.toPx()
                                drawRect(Color.Cyan, style = Stroke(strokeWidth))

                                //vertical lines
                                val verticalLines = 4
                                val verticalLineWidth = size.width / (verticalLines + 1)

                                repeat(verticalLines) { i ->

                                    val startX = verticalLineWidth * (i + 1)

                                    drawLine(
                                            Color.Cyan,
                                            start = Offset(startX, 0f),
                                            end = Offset(startX, size.height),
                                            strokeWidth = strokeWidth
                                    )
                                }

                                //Horizontal Lines

                                val horizontalLines = 3

                                val horizontalLineWidth = size.height / (horizontalLines + 1)

                                repeat(horizontalLines) {

                                    i ->

                                    val startY = horizontalLineWidth * (i + 1)

                                    drawLine(
                                            Color.Cyan,
                                            start = Offset(0f, startY),
                                            end = Offset(size.width, startY),
                                            strokeWidth = strokeWidth
                                    )
                                }
                            }

                        })
    }
}


fun generatePath():Path{

    val path = Path()
    path.moveTo(0f, 0f) // Start point
    path.cubicTo(400f, 50f, 250f, 250f, 350f, 150f) // Cubic curve
    path.cubicTo(400f, 50f, 250f, 250f, 350f, 150f) // Cubic curv
    return path
}



class TicketShape(private val cornerRadius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        TODO("Not yet implemented")
    }


}
@Preview
@Composable
fun WeatherWidgetPreviewLight() {

    WeatherWidget()
}