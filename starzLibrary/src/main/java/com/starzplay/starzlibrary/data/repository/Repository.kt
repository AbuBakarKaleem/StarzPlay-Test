package com.starzplay.starzlibrary.data.repository

import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.Movies
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getMovies(searchQuery: String, page: Int): Flow<DataState<Movies>>
}