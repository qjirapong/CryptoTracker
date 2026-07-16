package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import io.ktor.client.statement.HttpResponse
import com.plcoding.cryptotracker.core.domain.util.NetworkResult
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): NetworkResult<T, NetworkError>{
    val response = try {
        execute()
    }
    catch (e: UnresolvedAddressException) {
        return NetworkResult.Error(NetworkError.NO_INTERNET)
    }
    catch (e: SerializationException){
        return NetworkResult.Error(NetworkError.SERIALIZATION)
    }
    catch (e: Exception){
        //Make sure that Coroutine isn't cancelled
        currentCoroutineContext().ensureActive()
        return NetworkResult.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}