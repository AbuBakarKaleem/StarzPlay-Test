package com.starzplay.starzlibrary.data.usecases

import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaSearchResponse
import com.starzplay.starzlibrary.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchMultiSearchUseCase {
    suspend fun invoke(searchQuery: String, pageNo: Int): Flow<DataState<MediaSearchResponse>> = flow {
        Repository().getMovies(searchQuery = searchQuery, pageNo = pageNo).collect { response ->
            when (response) {
                is DataState.Success -> {
                    response.data?.let {
                        response.data.results.forEach { media ->
                            media.pageNo = response.data.page
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