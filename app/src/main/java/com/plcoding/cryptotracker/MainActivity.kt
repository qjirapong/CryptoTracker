package com.plcoding.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import com.plcoding.cryptotracker.core.presentation.util.toUiText
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.dimens
import com.plcoding.cryptotracker.ui.theme.rememberAppTypography
import com.plcoding.cryptotracker.util.AutoSizeText
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                val viewModel = koinViewModel<CoinListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                
                val context = LocalContext.current
                val snackBarHostState = remember { SnackbarHostState() }
                val typography = rememberAppTypography()

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is CoinListEvent.Error -> {
                            snackBarHostState.showSnackbar(
                                message = event.error.toUiText(context)
                            )
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
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CoinListScreen(
                        state = state,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}