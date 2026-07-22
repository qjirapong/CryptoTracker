package com.plcoding.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography

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
    val textStyle = rememberAppTypography().labelMedium
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
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.roundToPx()
    }
}