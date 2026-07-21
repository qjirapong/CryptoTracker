package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.qjirapong.network.HTTPClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HTTPClientFactory.create(CIO.create(), authToken = "5a086848684be24749a5d00f56b57498def81d8eb001b1bcfd118229b7f151fd")
    }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}