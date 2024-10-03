package com.starzplay.entertainment.interfaces

import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData

interface OnMediaClickListener {
    fun onMediaClick(movie: MoviesData)
}