package com.plcoding.cryptotracker.crypto.data.networking

import com.qjirapong.network.NetworkError
import com.qjirapong.network.NetworkResult
import com.qjirapong.network.map
import com.qjirapong.network.constructURL
import com.qjirapong.network.safeCall
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.mappers.toCoinPriceHistory
import com.plcoding.cryptotracker.crypto.data.networking.response.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.data.networking.response.CoinPriceHistoryResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinPriceHistory
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource (private val httpClient: HttpClient): CoinDataSource {
    override suspend fun getCoinList(): NetworkResult<List<Coin>, NetworkError> {
        return safeCall<CoinListResponseDto> {
            httpClient.get(
                urlString = constructURL("/assets", baseUrl = "https://rest.coincap.io/v3/")
            )
        }.map { responseDto ->
            responseDto.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinPriceHistory(
        coinID: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): NetworkResult<List<CoinPriceHistory>, NetworkError> {
        return safeCall<CoinPriceHistoryResponseDto> {
            httpClient.get(
                urlString = constructURL(
                    "/assets/$coinID/history",
                    baseUrl = "https://rest.coincap.io/v3/"
                )
            ){
                parameter("interval", "h6")
                parameter(
                    "start",
                    start
                        .withZoneSameInstant(ZoneId.of("UTC"))
                        .toInstant()
                        .toEpochMilli()
                )
                parameter(
                    "end",
                    end
                        .withZoneSameInstant(ZoneId.of("UTC"))
                        .toInstant()
                        .toEpochMilli()
                )
            }
        }.map { responseDto ->
            responseDto.data.map { it.toCoinPriceHistory() }
        }
    }
}