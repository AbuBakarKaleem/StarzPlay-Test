package com.starzplay.starzlibrary.di

import com.starzplay.starzlibrary.data.remote.ApiService
import com.starzplay.starzlibrary.data.repository.Repository
import com.starzplay.starzlibrary.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }
}
