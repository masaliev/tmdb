package com.github.masaliev.tmdb.data.remote.helper

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().addQueryParameter("api_key", apiKey).build()

        return chain.proceed(request.newBuilder().url(newUrl).build())
    }
}