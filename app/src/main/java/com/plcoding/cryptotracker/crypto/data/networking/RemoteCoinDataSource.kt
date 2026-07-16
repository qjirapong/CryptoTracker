package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.cryptotracker.core.data.networking.constructURL
import com.plcoding.cryptotracker.core.data.networking.safeCall
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.NetworkResult
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.networking.response.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource (private val httpClient: HttpClient): CoinDataSource {
    override suspend fun getCoinList(): NetworkResult<List<Coin>, NetworkError> {
        return safeCall<CoinListResponseDto> {
            httpClient.get(
                urlString = constructURL("/assets")
            )
        }.map { responseDto ->
            responseDto.data.map { it.toCoin() }
        }
    }
}