package com.plcoding.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.crypto.presentation.coin_details.components.InfoCard
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.crypto.presentation.model.toDisplayableNumber
import com.plcoding.cryptotracker.ui.theme.greenBackground

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
                adaptiveStyle = typography.headlineMedium,
                color = contentColor
            )
            AutoSizeText(
                text = coin.symbol,
                adaptiveStyle = typography.bodyLarge,
                color = contentColor
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formattedText = "$ " + coin.marketCapUSD.formatted,
                    icon = ImageVector.vectorResource(id = R.drawable.stock),
                    contentColor = contentColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    InfoCard(
                        title = stringResource(R.string.price),
                        formattedText = "$ " + coin.priceUSD.formatted,
                        icon = ImageVector.vectorResource(id = R.drawable.dollar),
                        contentColor = contentColor,
                        modifier = Modifier.weight(1f)
                    )
                    val absChangeFormatted = (
                            coin.priceUSD.value * (
                                    coin.changePercent24HR.value / 100
                            )
                    ).toDisplayableNumber()
                    val isPositive = coin.changePercent24HR.value > 0.0
                    val isZero = coin.changePercent24HR.value == 0.0
                    val changeContentColor = if (isPositive){
                        if (isSystemInDarkTheme()){
                            Color.Green
                        }
                        else{
                            greenBackground
                        }
                    }
                    else if (isZero){
                        Color.Yellow
                    }
                    else{
                        MaterialTheme.colorScheme.error
                    }
                    InfoCard(
                        title = stringResource(R.string.change_last_24h),
                        formattedText = "$ ${absChangeFormatted.formatted}",
                        icon = if (isPositive){
                            ImageVector.vectorResource(id = R.drawable.trending)
                        }
                        else{
                            ImageVector.vectorResource(id = R.drawable.trending_down)
                        },
                        contentColor = changeContentColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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