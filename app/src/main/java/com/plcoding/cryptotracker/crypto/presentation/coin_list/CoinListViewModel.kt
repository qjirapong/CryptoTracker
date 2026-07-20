package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(private val coinDataSource: CoinDataSource): ViewModel() {
    //Private, modifiable
    private val _state = MutableStateFlow(CoinListState())
    //Public, read-only
    val state = _state.onStart {
        loadCoinList()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListState()
    )

    private fun loadCoinList(){
        viewModelScope.launch{
            _state.update { it.copy(isLoading = true) }
            coinDataSource.getCoinList()
                .onSuccess { coinList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        coinList = coinList.map { coin -> coin.toCoinUI() }
                    )
                }
            }.onError {
                _state.update {
                    it.copy(
                        isLoading = false,
                        coinList = emptyList()
                    )
                }
            }
        }
    }
}