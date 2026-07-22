package com.plcoding.cryptotracker.crypto.domain

import java.time.ZonedDateTime

data class CoinPriceHistory(
    val priceUSD: Double,
    val dateTime: ZonedDateTime
)