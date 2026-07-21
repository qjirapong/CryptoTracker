package com.plcoding.cryptotracker.crypto.data.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponseDto (
    val data: List<CoinDto>
)