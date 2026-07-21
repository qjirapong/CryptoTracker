package com.qjirapong.network

import android.util.Log
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): NetworkResult<T, NetworkError>{
    return try {
        val response = execute()
        responseToResult(response)
    }
    catch (e: UnresolvedAddressException) {
        Log.e("safeCall", "UnresolvedAddressException: ${e.message}", e)
        NetworkResult.Error(NetworkError.NO_INTERNET)
    }
    catch (e: SerializationException){
        Log.e("safeCall", "SerializationException: ${e.message}", e)
        NetworkResult.Error(NetworkError.SERIALIZATION)
    }
    catch (e: Exception){
        Log.e("safeCall", "Unknown exception: ${e.message}", e)
        //Make sure that Coroutine isn't cancelled
        currentCoroutineContext().ensureActive()
        NetworkResult.Error(NetworkError.UNKNOWN)
    }
}
