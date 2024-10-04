package com.starzplay.entertainment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailInfo(
    var title: String = "",
    var imageUrl: String = "",
    var description: String = "",
    var mediaType: String = ""
) : Parcelable
