package com.starzplay.library.data.repository

import com.starzplay.library.data.remote.DataState
import com.starzplay.library.data.remote.ResponseModel.Movies
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getMovies(searchQuery: String, page: Int): Flow<DataState<Movies>>
}