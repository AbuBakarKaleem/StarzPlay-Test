package com.starzplay.starzlibrary.di


import com.google.gson.Gson
import com.starzplay.starzlibrary.data.remote.ApiService
import com.starzplay.starzlibrary.helper.Constants.BASE_URL
import com.starzplay.starzlibrary.helper.Constants.TIME_OUT
import com.starzplay.starzlibrary.helper.Constants.access_token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {

        // Logging interceptor
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val apiInterceptor = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $access_token").build()
            )
        }

        return OkHttpClient.Builder().readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).addInterceptor(apiInterceptor)
            .addInterceptor(loggingInterceptor).build()
        //.addInterceptor(ConnectivityInterceptor()).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson())).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
