package com.plcoding.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText

@Composable
fun CoinDetailsScreen(state: CoinListState,
                      modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()){
        Color.White
    }
    else{
        Color.Black
    }
    val typography = rememberAppTypography()
    if (state.isLoading){
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
    else if (state.selectedCoin != null){
        val coin = state.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.dimens.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(MaterialTheme.dimens.coinIconSize),
                tint = MaterialTheme.colorScheme.primary
            )
            AutoSizeText(
                text = coin.name,
                adaptiveStyle = typography.bodyLarge,
                color = contentColor
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailsScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailsScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}