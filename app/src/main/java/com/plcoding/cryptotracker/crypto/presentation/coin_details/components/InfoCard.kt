package com.plcoding.cryptotracker.crypto.presentation.coin_details.components


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.ui.theme.AdaptiveTextStyle
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText

@Composable
fun InfoCard(title: String,
             formattedText: String,
             icon: ImageVector,
             contentColor: Color = MaterialTheme.colorScheme.onSurface,
             formattedTextStyle: AdaptiveTextStyle = rememberAppTypography().bodyMedium,
             modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(MaterialTheme.dimens.spaceMediumSmall)
        .shadow(elevation = MaterialTheme.dimens.spaceSmall,
            shape = RectangleShape,
            ambientColor = MaterialTheme.colorScheme.primary,
            spotColor = MaterialTheme.colorScheme.primary),
        shape = RectangleShape,
        border = BorderStroke(
            width = MaterialTheme.dimens.spaceExtraSmall / 4,
            color = MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = contentColor)){
        AnimatedContent(
            targetState = icon,
            modifier = Modifier.align(Alignment.CenterHorizontally)) { icon ->
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(MaterialTheme.dimens.coinIconSize)
                    .padding(MaterialTheme.dimens.spaceSmall)
            )
        }
        AnimatedContent(
            targetState = formattedText,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)) { text ->
            AutoSizeText(
                text = text,
                adaptiveStyle = formattedTextStyle,
                alignment = Alignment.Center,
                color = contentColor,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.spaceSmall)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceSmall))
        AutoSizeText(
            text = title,
            adaptiveStyle = rememberAppTypography().bodySmall,
            alignment = Alignment.Center,
            color = contentColor,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = MaterialTheme.dimens.spaceMediumSmall)
                .padding(bottom = MaterialTheme.dimens.spaceMediumSmall)
        )
    }
}

@PreviewLightDark
@Composable
private fun InfoCardPreview() {
    CryptoTrackerTheme {
        InfoCard(
            title = "Market Cap",
            formattedText = "$1,000,000,000",
            icon = ImageVector.vectorResource(id = com.plcoding.cryptotracker.R.drawable.dollar)
        )
    }
}