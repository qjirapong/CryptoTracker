package com.plcoding.cryptotracker.crypto.presentation.coin_details.components


import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.ui.theme.AdaptiveTextStyle
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography

@Composable
fun InfoCard(title: String,
             formattedText: String,
             icon: ImageVector,
             contentColor: Color = MaterialTheme.colorScheme.onSurface,
             formattedTextStyle: AdaptiveTextStyle = rememberAppTypography().bodyMedium,
             modifier: Modifier = Modifier) {
    Card(modifier = modifier) {

    }
}

@PreviewLightDark
@Composable
private fun InfoCardPreview() {

}