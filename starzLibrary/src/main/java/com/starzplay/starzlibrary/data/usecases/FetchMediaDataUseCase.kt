package com.starzplay.starzlibrary.data.usecases

import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaData
import com.starzplay.starzlibrary.data.repository.Repository
import com.starzplay.starzlibrary.helper.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchMediaDataUseCase {

    suspend fun invoke(
        searchQuery: String, pageNo: Int, mediaType: String
    ): Flow<DataState<List<MediaData>>> = flow {
        Repository().getMediaData(
            searchQuery = searchQuery,
            pageNo = pageNo,
            endPoint = Constants.SEARCH_END_POINT + mediaType
        ).collect { response ->
            when (response) {
                is DataState.Success -> {
                    response.data?.let {
                        response.data.results.forEach { media ->
                            media.pageNo = response.data.page
                            media.mediaType = mediaType
                        }
                        emit(DataState.Success(response.data.results))
                    }
                }

                is DataState.Error -> {
                    emit(DataState.Error(response.error))
                }
            }
        }
    }
}