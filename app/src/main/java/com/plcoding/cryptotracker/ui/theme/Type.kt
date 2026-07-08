package com.plcoding.cryptotracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import com.plcoding.cryptotracker.R

val SpaceMono = FontFamily(
    Font(
        resId = R.font.space_mono_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.space_mono_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.space_mono_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.space_mono_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
)

@Composable
fun Dp.toTextUnit(): TextUnit {
    return with(LocalDensity.current) { this@toTextUnit.toSp() }
}

data class AdaptiveTextStyle(
    val style: TextStyle,
    val minFontSize: TextUnit = TextUnit.Unspecified,
    val maxFontSize: TextUnit = style.fontSize
)

data class AppTypography(
    val bodySmall: AdaptiveTextStyle,
    val bodyMedium: AdaptiveTextStyle,
    val bodyLarge: AdaptiveTextStyle,
    val labelMedium: AdaptiveTextStyle,
    val headlineMedium: AdaptiveTextStyle
)

@Composable
fun rememberAppTypography(): AppTypography {
    return AppTypography(
        bodySmall = AdaptiveTextStyle(
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Light,
                fontSize = 12.dp.toTextUnit()
            ),
            minFontSize = 8.dp.toTextUnit()
        ),
        bodyMedium = AdaptiveTextStyle(
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Normal,
                fontSize = 14.dp.toTextUnit()
            ),
            minFontSize = 10.dp.toTextUnit()
        ),
        bodyLarge = AdaptiveTextStyle(
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Normal,
                fontSize = 16.dp.toTextUnit(),
                lineHeight = 24.dp.toTextUnit(),
                letterSpacing = 0.5.dp.toTextUnit()
            ),
            minFontSize = 11.dp.toTextUnit()
        ),
        labelMedium = AdaptiveTextStyle(
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Normal,
                fontSize = 14.dp.toTextUnit()
            ),
            minFontSize = 10.dp.toTextUnit()
        ),
        headlineMedium = AdaptiveTextStyle(
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Bold,
                fontSize = 18.dp.toTextUnit()
            ),
            minFontSize = 12.dp.toTextUnit()
        )
    )
}