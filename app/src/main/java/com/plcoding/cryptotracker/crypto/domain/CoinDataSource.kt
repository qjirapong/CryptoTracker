package com.plcoding.cryptotracker.crypto.domain

import com.qjirapong.network.NetworkError
import com.qjirapong.network.NetworkResult
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoinList(): NetworkResult<List<Coin>, NetworkError>
    suspend fun getCoinPriceHistory(
        coinID: String, start: ZonedDateTime, end: ZonedDateTime
    ): NetworkResult<List<CoinPriceHistory>, NetworkError>
}