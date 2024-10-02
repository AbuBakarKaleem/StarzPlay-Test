package com.starzplay.library.di

import com.starzplay.library.data.remote.ApiService
import com.starzplay.library.data.repository.Repository
import com.starzplay.library.data.repository.RepositoryImpl
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
