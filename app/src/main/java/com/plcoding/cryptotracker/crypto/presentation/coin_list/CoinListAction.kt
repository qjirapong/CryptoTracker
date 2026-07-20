package com.plcoding.cryptotracker.crypto.presentation.coin_list

import com.plcoding.cryptotracker.crypto.presentation.model.CoinUI

sealed interface CoinListAction {
    data class OnCoinItemCLick(val coinUI: CoinUI): CoinListAction
    data object OnRefresh: CoinListAction
}