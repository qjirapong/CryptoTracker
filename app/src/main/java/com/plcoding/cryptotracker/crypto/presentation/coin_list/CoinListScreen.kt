package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens

@Composable
fun CoinListScreen(state: CoinListState,
                   onAction: (CoinListAction) -> Unit,
                   modifier: Modifier = Modifier,
                   lazyListState: LazyListState = rememberLazyListState()) {
    if (state.isLoading && state.coinList.isEmpty()){
        Box(modifier = modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else {
        LazyColumn(
            state = lazyListState,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceSmall)) {
            items(state.coinList, key = { it.id }) { coinUI ->
                CoinListItem(
                    coinUI = coinUI,
                    onClick = {
                        onAction(CoinListAction.OnCoinItemCLick(coinUI))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coinList = (1..10).map {
                    previewCoin.copy(
                        id = it.toString(),
                        name = "Coin $it",
                        symbol = "C$it"
                    )
                }
            ),
            onAction = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}