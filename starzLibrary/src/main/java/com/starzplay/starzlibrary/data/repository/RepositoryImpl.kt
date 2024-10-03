package com.starzplay.library.data.repository

import com.starzplay.library.data.remote.ApiService
import com.starzplay.library.data.remote.DataState
import com.starzplay.library.data.remote.ResponseModel.Movies
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {
    override suspend fun getMovies(searchQuery: String, page: Int) = flow {
        emit(DataState.Loading())
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