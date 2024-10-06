package com.starzplay.starzlibrary.data.repository

import com.starzplay.starzlibrary.data.remote.ApiClient
import com.starzplay.starzlibrary.data.remote.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Repository {
    private val service = ApiClient.apiService
    suspend fun getMovies(searchQuery: String, pageNo: Int) = flow {
        val response = service.multiSearch(query = searchQuery, pageNo = pageNo)
        emit(
            if (response.isSuccessful) {
                DataState.Success(response.body())
            } else {
                val errorBody = response.errorBody()?.string()
                DataState.Error(DataState.CustomMessages.Unauthorized(errorBody.toString()))
            }
        )
    }.catch {
        this.emit(
            DataState.Error(
                DataState.CustomMessages.SomethingWentWrong(
                    it.message ?: DataState.CustomMessages.BadRequest.toString()
                )
            )
        )
    }

    suspend fun getMediaData(searchQuery: String, pageNo: Int, endPoint: String) = flow {
        val response =
            service.getMediaData(query = searchQuery, pageNo = pageNo, endPoint = endPoint)
        emit(
            if (response.isSuccessful) {
                DataState.Success(response.body())
            } else {
                val errorBody = response.errorBody()?.string()
                DataState.Error(DataState.CustomMessages.Unauthorized(errorBody.toString()))
            }
        )
    }.catch {
        this.emit(
            DataState.Error(
                DataState.CustomMessages.SomethingWentWrong(
                    it.message ?: DataState.CustomMessages.BadRequest.toString()
                )
            )
        )
    }

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance() = INSTANCE ?: Repository().also { INSTANCE = it }
    }
}