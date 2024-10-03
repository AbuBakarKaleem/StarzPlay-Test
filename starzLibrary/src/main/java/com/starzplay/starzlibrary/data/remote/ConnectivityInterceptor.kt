package com.starzplay.starzlibrary.data.remote

import okhttp3.Interceptor
import okhttp3.Response

open class ConnectivityInterceptor : Interceptor {

    /*private val isConnected: Boolean
        get() {
            return isInternetAvailable(app)
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw Throwable(DataState.CustomMessages.NoInternet.message)
        }
        return chain.proceed(originalRequest)
    }*/
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}

