package com.starzplay.starzlibrary.data.remote.ResponseModel


import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page") val page: Int,

    @SerializedName("results") val results: List<MoviesData>,

    @SerializedName("total_pages") val totalPages: Int,

    @SerializedName("total_results") val totalResults: Int
)

data class MoviesData(
    @SerializedName("backdrop_path") val backdropPath: String?,

    @SerializedName("id") val id: Int,

    @SerializedName("name") val name: String,

    @SerializedName("original_name") val originalName: String,

    @SerializedName("overview") val overview: String?,

    @SerializedName("poster_path") val posterPath: String?,

    @SerializedName("media_type") val mediaType: String,

    @SerializedName("adult") val adult: Boolean,

    @SerializedName("original_language") val originalLanguage: String,

    @SerializedName("genre_ids") val genreIds: List<Int>,

    @SerializedName("popularity") val popularity: Double,

    @SerializedName("first_air_date") val firstAirDate: String?,

    @SerializedName("vote_average") val voteAverage: Double,

    @SerializedName("vote_count") val voteCount: Int,

    @SerializedName("origin_country") val originCountry: List<String>,

    // For person type (optional fields)
    @SerializedName("known_for") val knownFor: List<KnownFor>? = null,

    @SerializedName("title") val title: String? = null,

    @SerializedName("original_title") val originalTitle: String? = null,

    @SerializedName("release_date") val releaseDate: String? = null,

    @SerializedName("video") val video: Boolean? = null
)

data class KnownFor(
    @SerializedName("backdrop_path") val backdropPath: String?,

    @SerializedName("id") val id: Int,

    @SerializedName("name") val name: String?,

    @SerializedName("original_name") val originalName: String?,

    @SerializedName("overview") val overview: String,

    @SerializedName("poster_path") val posterPath: String?,

    @SerializedName("media_type") val mediaType: String,

    @SerializedName("original_language") val originalLanguage: String,

    @SerializedName("genre_ids") val genreIds: List<Int>,

    @SerializedName("popularity") val popularity: Double,

    @SerializedName("first_air_date") val firstAirDate: String?,

    @SerializedName("vote_average") val voteAverage: Double,

    @SerializedName("vote_count") val voteCount: Int,

    @SerializedName("origin_country") val originCountry: List<String>,

    @SerializedName("title") val title: String? = null,

    @SerializedName("original_title") val originalTitle: String? = null,

    @SerializedName("release_date") val releaseDate: String? = null,

    @SerializedName("video") val video: Boolean? = null
)

