package com.plcoding.cryptotracker.crypto.data.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceHistoryDto(
    val priceUsd: Double,
    val time: Long
)