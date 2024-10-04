package com.starzplay.entertainment.extension

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.starzplay.starzlibrary.helper.Constants.BASE_URL

fun Fragment.longToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Context.getColorResource(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getImageUrl(imagePath: String, isRemoveSlash: Boolean = true): String {
    val resultString = if (isRemoveSlash) imagePath.removePrefix("/") else imagePath
    return BASE_URL + resultString
}
