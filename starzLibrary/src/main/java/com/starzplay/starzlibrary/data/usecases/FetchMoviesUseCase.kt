package com.starzplay.starzlibrary.data.usecases

import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.Movies
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData
import com.starzplay.starzlibrary.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchMoviesUseCase {
    private var lastPageData = mutableListOf<MoviesData>()
    suspend fun invoke(searchQuery: String, pageNo: Int): Flow<DataState<Movies>> = flow {
        Repository().getMovies(searchQuery = searchQuery, pageNo = pageNo).collect { response ->
            when (response) {
                is DataState.Success -> {
                    response.data?.let {
                        if (lastPageData.isEmpty()) {
                            lastPageData = it.results.toMutableList()
                        } else {
                            lastPageData.addAll(it.results.toMutableList())
                            it.results = lastPageData
                        }
                        emit(DataState.Success(it))
                    }
                }

                is DataState.Error -> {
                    emit(DataState.Error(response.error))
                }
            }
        }
    }
}