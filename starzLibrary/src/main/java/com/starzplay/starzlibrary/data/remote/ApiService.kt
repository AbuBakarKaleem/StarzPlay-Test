package com.starzplay.starzlibrary.data.remote

import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaSearchResponse
import com.starzplay.starzlibrary.helper.Constants.MULTI_SEARCH_END_POINT
import com.starzplay.starzlibrary.helper.Constants.access_token
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @Headers(
        "Accept: application/json", "Authorization: Bearer $access_token"
    )
    @GET(MULTI_SEARCH_END_POINT)
    suspend fun multiSearch(
        @Query("query", encoded = true) query: String,
        @Query("include_adult") adult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") pageNo: Int = 1,
    ): Response<MediaSearchResponse>

    @Headers(
        "Accept: application/json", "Authorization: Bearer $access_token"
    )
    @GET
    suspend fun getMediaData(
        @Url endPoint: String,
        @Query("query", encoded = true) query: String,
        @Query("include_adult") adult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") pageNo: Int = 1
    ): Response<MediaSearchResponse>
}