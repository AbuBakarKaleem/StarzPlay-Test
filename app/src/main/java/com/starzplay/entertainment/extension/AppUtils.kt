package com.starzplay.entertainment.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.starzplay.entertainment.R
import com.starzplay.starzlibrary.helper.Constants.BASE_URL
import com.starzplay.starzlibrary.helper.gone
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

fun Context.loadImage(
    imageUrl: String, imageHolder: ImageView, progressBar: ProgressBar
) {
    if (imageUrl.isEmpty()) return

    Glide.with(this).load(imageUrl.getImageUrl()).listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
        ): Boolean {
            //Image load fail
            imageHolder.setImageDrawable(
                ContextCompat.getDrawable(
                    this@loadImage, R.drawable.ic_error_image
                )
            )
            progressBar.gone()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            // Image loaded successfully
            progressBar.gone()
            return false
        }

    }).into(imageHolder)

}
