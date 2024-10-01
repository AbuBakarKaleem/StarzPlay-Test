package com.starzplay.library.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.starzplay.library.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException(context.getString(R.string.NO_INTERNET_ERROR))
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

class NoInternetException(message: String) : Exception(message)

