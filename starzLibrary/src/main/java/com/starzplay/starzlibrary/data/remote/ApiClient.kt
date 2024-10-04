package com.starzplay.starzlibrary.data.remote

import com.starzplay.starzlibrary.helper.Constants.BASE_URL
import com.starzplay.starzlibrary.helper.Constants.access_token
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
    }

    private val networkInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().addHeader(
            "Authorization", "Bearer $access_token"
        ).build()
        chain.proceed(request)
    }

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(networkInterceptor)
            .build()

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
