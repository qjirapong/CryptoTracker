package com.plcoding.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.domain.CoinPriceHistory
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun LineChart(dataPoints: List<PriceHistoryDataPoint>,
              style: ChartStyle,
              visibleDataPointsIndices: IntRange,
              unit: String,
              modifier: Modifier = Modifier,
              selectedDataPoint: PriceHistoryDataPoint? = null,
              onSelectedDataPoint: (PriceHistoryDataPoint) -> Unit = {},
              onXLabelWidthChange: (Float) -> Unit = {},
              showHelperLines: Boolean = true) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )
    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices){
        //Slice the (probably) huge list
        dataPoints.slice(visibleDataPointsIndices)
    }
    val maxYValue = remember(visibleDataPoints){
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }
    val minYValue = remember(visibleDataPoints){
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }
    val measurer = rememberTextMeasurer()
    var xLabelWidth by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }
    val selectedDataPointIndex = remember {
        mutableStateOf(listOf<PriceHistoryDataPoint>())
    }
    val drawPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }

    Canvas(
        modifier = modifier.fillMaxSize()) {
        val minLabelSpacingY = style.minYLabelSpacing.roundToPx()
        val verticalPaddingPx = style.verticalPadding.roundToPx()
        val horizontalPaddingPx = style.horizontalPadding.roundToPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.toPx()

        val xLabelTextLayoutResults = visibleDataPoints.map{
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }
        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = if (maxXLabelLineCount > 0){
            maxXLabelHeight / maxXLabelLineCount
        }
        else{
            0
        }

        val viewPortHeightPx = size.height -
                (maxXLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight + xAxisLabelSpacingPx)
        val viewPortRightX = size.width
        val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = (2 * horizontalPaddingPx).toFloat()
        val viewPort = Rect(
            viewPortLeftX,
            viewPortTopY,
            viewPortRightX,
            viewPortBottomY
        )
        drawRect(
            color = Color.Green,
            topLeft = viewPort.topLeft,
            size = viewPort.size
        )
        xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
        xLabelTextLayoutResults.forEachIndexed { index, result ->
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = viewPortLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index,
                    y = viewPortBottomY + xAxisLabelSpacingPx
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LineChartPreview() {
    CryptoTrackerTheme {
        val coinHistoryRandomized = remember {
            (1 .. 20).map{
                CoinPriceHistory(
                    priceUSD = Random.nextFloat() * 1000.0,
                    dateTime = ZonedDateTime.now().plusHours(it.toLong())
                )
            }
        }
        val style = ChartStyle(
            chartLineColor = Color.Black,
            unselectedColor = Color(0xFFB0BEC5),
            selectedColor = Color.Black,
            labelFontSize = 14.sp,
            minYLabelSpacing = 20.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 8.dp,
            xAxisLabelSpacing = 8.dp,
            axisLinesThicknessPx = 2f,
            helperLinesThicknessPx = 1f,
        )
        val dataPoints = remember {
            coinHistoryRandomized.map {
                PriceHistoryDataPoint(
                    x = it.dateTime.hour.toFloat(),
                    y = it.priceUSD.toFloat(),
                    xLabel = DateTimeFormatter
                        .ofPattern("ha\nyd")
                        .format(it.dateTime)
                )
            }
        }
        LineChart(
            dataPoints = dataPoints,
            style = style,
            visibleDataPointsIndices = dataPoints.indices,
            unit = "$",
            modifier = Modifier.width(700.dp).height(300.dp).background(Color.White),
            selectedDataPoint = dataPoints[1]
        )
    }
}