package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.plcoding.cryptotracker.crypto.presentation.model.DisplayableNumber
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.greenBackground
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText

@Composable
fun PriceChange(
    change: DisplayableNumber,
    modifier: Modifier = Modifier) {
    val typography = rememberAppTypography()
    val textColor = if (change.value < 0.0){
        MaterialTheme.colorScheme.onErrorContainer
    }
    else if (change.value == 0.0){
        Color.Yellow
    }
    else{
        Color.Green
    }
    val backgroundColor = if (change.value < 0.0){
        MaterialTheme.colorScheme.errorContainer
    }
    else if (change.value == 0.0){
        MaterialTheme.colorScheme.outlineVariant
    }
    else{
        greenBackground
    }

    Row(modifier = modifier
        .clip(RoundedCornerShape(100f))
        .background(backgroundColor)
        .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (change.value < 0.0) {
                Icons.Default.KeyboardArrowDown
            }
            else if (change.value == 0.0){
                Icons.Default.Code
            }
            else {
                Icons.Default.KeyboardArrowUp
            },
            null,
            modifier = Modifier.size(20.dp),
            tint = textColor
        )
        AutoSizeText(
            modifier = modifier.padding(horizontal = 10.dp),
            text = change.formatted,
            adaptiveStyle = typography.bodyMedium,
            maxLines = 1,
            color = textColor
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceNoChangePreview() {
    CryptoTrackerTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 0.0,
                formatted = "0.00"
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeDownPreview() {
    CryptoTrackerTheme {
        PriceChange(
            change = DisplayableNumber(
                value = -10.0,
                formatted = "-10.00"
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeUpPreview() {
    CryptoTrackerTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 10.0,
                formatted = "+10.00"
            )
        )
    }
}