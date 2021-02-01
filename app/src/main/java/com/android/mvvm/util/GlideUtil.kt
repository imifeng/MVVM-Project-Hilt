package com.android.mvvm.util

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import com.android.mvvm.App
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


fun ImageView.loadRound(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.circleCropTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRect(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.centerCropTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRectFitCenter(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.fitCenterTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRoundCorner(url: String?, roundedCorners: Int) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions().transform(CenterCrop(), RoundedCorners(roundedCorners))
        loadImage(context, this, url, requestOptions)
    }
}

private fun loadImage(
    context: Context,
    view: ImageView,
    url: String?,
    requestOptions: RequestOptions,
) {
    Glide.with(context)
        .load(url)
        .thumbnail(0.2f)
        .apply(requestOptions)
        .into(view)
}

fun loadGif(context: Context, gifView: ImageView, gifDrawable: Int, loopCount: Int) {
    Glide.with(context).asGif().load(gifDrawable)
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<GifDrawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable,
                model: Any,
                target: Target<GifDrawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                resource.setLoopCount(loopCount)
                return false
            }
        }).into(gifView).clearOnDetach()
}

fun clearMemory(context: Context) {
    Glide.get(context).clearMemory()
}
