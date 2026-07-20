package com.plcoding.cryptotracker.crypto.domain

import com.qjirapong.network.NetworkError
import com.qjirapong.network.NetworkResult

interface CoinDataSource {
    suspend fun getCoinList(): NetworkResult<List<Coin>, NetworkError>
}