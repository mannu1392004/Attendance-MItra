package com.example.savera.Experiment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showSystemUi = true)
@Composable
fun Drawshape() {
    Box(modifier =
    Modifier
        .fillMaxWidth()
        .height(90.dp)
        .drawBehind {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(1100f, 0f)
                lineTo(1100f, 300f)
                lineTo(00f, 300f)
                lineTo(100f, 150f)
                close()
            }
            drawPath(
                path = path,
                color = Color.Red
            )

        }
    )

}