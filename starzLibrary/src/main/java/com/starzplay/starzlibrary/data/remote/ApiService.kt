package com.starzplay.starzlibrary.data.remote

import com.starzplay.starzlibrary.data.remote.ResponseModel.Movies
import com.starzplay.starzlibrary.helper.Constants.MULTI_MOVIE_END_POINT
import com.starzplay.starzlibrary.helper.Constants.access_token
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "Accept: application/json", "Authorization: Bearer $access_token"
    )
    @GET(MULTI_MOVIE_END_POINT)
    suspend fun getMovies(
        @Query("query", encoded = true) query: String,
        @Query("include_adult") adult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") pageNo: Int = 1,
    ): Response<Movies>
}