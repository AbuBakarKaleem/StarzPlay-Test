package com.starzplay.library.data.usecases

import com.starzplay.library.data.remote.DataState
import com.starzplay.library.data.remote.ResponseModel.Movies
import com.starzplay.library.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun invoke(searchQuery: String, page: Int): Flow<DataState<Movies>> = flow {
        repository.getMovies(searchQuery = searchQuery, page = page).collect { response ->
            when (response) {
                is DataState.Success -> {
                    response.data?.let { it ->
                        emit(DataState.Success(it))
                    }
                }

                is DataState.Error -> {
                    emit(DataState.Error(response.error))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                else -> {
                    emit(DataState.Error(response.error))
                }
            }
        }
    }
}