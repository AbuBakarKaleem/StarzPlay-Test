package com.starzplay.starzlibrary.data.repository

import com.starzplay.starzlibrary.data.remote.ApiService
import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.Movies
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {
    override suspend fun getMovies(searchQuery: String, page: Int) = flow {
        val response = apiService.getMovies(query = searchQuery, page = page)
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
            DataState.Error<Movies>(
                DataState.CustomMessages.SomethingWentWrong(
                    it.message ?: DataState.CustomMessages.BadRequest.toString()
                )
            )
        )
    }

}