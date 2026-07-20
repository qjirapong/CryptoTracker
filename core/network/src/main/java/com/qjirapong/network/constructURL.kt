package com.qjirapong.network

fun constructURL(url: String, baseUrl: String): String {
    return when {
        url.contains(baseUrl) -> url
        url.startsWith("/") -> baseUrl + url.drop(1)
        else -> baseUrl + url
    }
}
