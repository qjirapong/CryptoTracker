package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.networking.response.CoinDto
import com.plcoding.cryptotracker.crypto.data.networking.response.CoinPriceHistoryDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPriceHistory
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUSD = marketCapUsd,
        priceUSD = priceUsd,
        changePercent24HR = changePercent24Hr
    )
}

fun CoinPriceHistoryDto.toCoinPriceHistory(): CoinPriceHistory {
    return CoinPriceHistory(
        priceUSD = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
    )
}