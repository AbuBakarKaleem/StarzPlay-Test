package com.starzplay.entertainment.extension

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.starzplay.entertainment.R
import com.starzplay.starzlibrary.helper.Constants
import com.starzplay.starzlibrary.helper.Constants.BASE_URL
import java.util.Locale

fun Fragment.longToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Context.getColorResource(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun String.getImageUrl(isRemoveSlash: Boolean = true): String {
    val resultString = if (this.contains("/") && isRemoveSlash) this.removePrefix("/") else this
    return BASE_URL + resultString
}

fun String.toSentenceCase(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}

fun Context.loadImage(imageUrl: String, imageHolder: ImageView) {
    if (imageUrl.isEmpty()) return

    Glide.with(this).load(Constants.IMAGES_BASE_URL + imageUrl).error(R.drawable.ic_error_image)
        .placeholder(R.drawable.ic_media_placeholder).into(imageHolder)

}
