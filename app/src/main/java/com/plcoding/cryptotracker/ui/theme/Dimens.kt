package com.plcoding.cryptotracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMediumSmall: Dp = 10.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 24.dp,
    val spaceExtraLarge: Dp = 32.dp,
    
    // Component specific dimensions
    val coinIconSize: Dp = 85.dp,
    val priceChangeIconSize: Dp = 20.dp,
    val priceChangeChipSize: Dp = 115.dp,
    val boxSize: Dp = 140.dp
)

val LocalDimensions = staticCompositionLocalOf { Dimensions() }
