package com.plcoding.cryptotracker.crypto.presentation.model

import androidx.annotation.DrawableRes

data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUSD: DisplayableNumber,
    val priceUSD: DisplayableNumber,
    val changePercent24HR: DisplayableNumber,
    @param:DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)