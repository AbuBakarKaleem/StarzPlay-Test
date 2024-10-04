package com.starzplay.starzlibrary.data.repository

import com.starzplay.starzlibrary.data.remote.ApiClient
import com.starzplay.starzlibrary.data.remote.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Repository {
    private val service = ApiClient.apiService
    suspend fun getMovies(searchQuery: String, pageNo: Int) = flow {
        val response = service.getMovies(query = searchQuery, pageNo = pageNo)
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
        val instance: Repository by lazy { Repository() }
    }

}