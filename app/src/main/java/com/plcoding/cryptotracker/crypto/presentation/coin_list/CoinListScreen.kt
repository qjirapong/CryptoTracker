package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptotracker.core.presentation.util.toUiText
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CoinListScreen(state: CoinListState,
                   events: Flow<CoinListEvent>,
                   modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val typography = rememberAppTypography()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(lifecycleOwner.lifecycle){
        //Listen to the event only when the lifecycle has been started
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            events.collect { event ->
                when(event){
                    is CoinListEvent.Error -> {
                        // Show error message via snackBarHostState
                        snackBarHostState.showSnackbar(
                            message = event.error.toUiText(context)
                        )
                    }
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackBarData ->
                    Snackbar(
                        modifier = Modifier.padding(MaterialTheme.dimens.spaceSmall),
                        content = {
                            AutoSizeText(
                                text = snackBarData.visuals.message,
                                adaptiveStyle = typography.bodyMedium,
                                maxLines = 2
                            )
                        }
                    )
                }
            )
        },
        modifier = modifier) { padding ->
        if (state.isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceSmall)) {
                items(state.coinList) { coinUI ->
                    CoinListItem(
                        coinUI = coinUI,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
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
            events = emptyFlow(),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}