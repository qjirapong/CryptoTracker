package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qjirapong.network.onError
import com.qjirapong.network.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction){
        when(action){
            is CoinListAction.OnCoinItemCLick -> {
                // Handle coin item click
                _state.update {
                    it.copy(selectedCoin = action.coinUI)
                }
            }
            CoinListAction.OnRefresh -> {
                loadCoinList()
            }
        }
    }

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
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        coinList = emptyList()
                    )
                }
                //Send it once, with error details. No sending even if it's config changes.
                _events.send(CoinListEvent.Error(error))
            }
        }
    }
}