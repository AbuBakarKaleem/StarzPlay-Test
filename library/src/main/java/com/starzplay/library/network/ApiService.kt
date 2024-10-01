package com.starzplay.library.network

import com.starzplay.library.data.ResponseModel.MovieResponse
import com.starzplay.library.helper.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/multi")
    suspend fun getYourData(
        @Query("api_key") apiKey: String = API_KEY, @Query("query") query: String
    ): MovieResponse
}