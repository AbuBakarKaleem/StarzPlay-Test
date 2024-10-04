package com.starzplay.entertainment.module

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class GlideHeaderInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken") // Add access token as a header
            .build()
        return chain.proceed(newRequest)
    }
}
fun getOkHttpClient(accessToken: String): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(GlideHeaderInterceptor(accessToken))
        .build()
}