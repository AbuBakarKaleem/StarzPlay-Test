package com.starzplay.library.data.model

data class Movie(
    val id: Int,
    val media_type: String,
    val title: String?,
    val name: String?,
    val overview: String,
    val poster_path: String?
)
