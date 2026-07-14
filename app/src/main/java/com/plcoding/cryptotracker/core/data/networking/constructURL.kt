package com.plcoding.cryptotracker.core.data.networking

fun constructURL(url: String): String{
    val baseURL = "api.coincap.io/v2/"
    return when {
        url.contains(baseURL) -> url
        url.startsWith("/") -> baseURL + url.drop(1)
        else -> baseURL + url
    }
}