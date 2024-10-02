package com.starzplay.library.data.remote

import com.starzplay.library.LibraryApp
import com.starzplay.library.helper.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

open class ConnectivityInterceptor : Interceptor {

    private val isConnected: Boolean
        get() {
            return isInternetAvailable(LibraryApp.getInstance())
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw Throwable(DataState.CustomMessages.NoInternet.message)
        }
        return chain.proceed(originalRequest)
    }
}

