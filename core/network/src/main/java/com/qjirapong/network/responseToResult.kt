package com.qjirapong.network

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> responseToResult(response: HttpResponse): NetworkResult<T, NetworkError>{
    return when(response.status.value){
        in 200..299 -> {
            try {
                val data = response.body<T>()
                NetworkResult.Success(data)
            }
            catch (e: NoTransformationFoundException){
                NetworkResult.Error(NetworkError.SERIALIZATION)
            }
            try {
                NetworkResult.Success(response.body<T>())
            }
            catch (e: SerializationException) {
                NetworkResult.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> NetworkResult.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> NetworkResult.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> NetworkResult.Error(NetworkError.SERVER_ERROR)
        else -> NetworkResult.Error(NetworkError.UNKNOWN)
    }
}
