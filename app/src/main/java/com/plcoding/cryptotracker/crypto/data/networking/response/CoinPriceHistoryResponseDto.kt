package com.plcoding.cryptotracker.crypto.data.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceHistoryResponseDto (
    val data: List<CoinPriceHistoryDto>
)