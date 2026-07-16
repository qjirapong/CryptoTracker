package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.NetworkResult

interface CoinDataSource {
    suspend fun getCoinList(): NetworkResult<List<Coin>, NetworkError>
}