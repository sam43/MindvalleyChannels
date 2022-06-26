package com.sam43.mindvalleychannels.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sam43.mindvalleychannels.R

fun loadImage(iv: ImageView, url: String?, placeholder: Int = R.drawable.placeholder) =
    Glide.with(iv.context)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholder)
        .into(iv)

fun loadImageCircular(iv: ImageView, url: String?, placeholder: Int = R.drawable.ic_icon_asset_circular) =
    Glide.with(iv.context)
        .load(url)
        .dontAnimate()
        .apply(RequestOptions.circleCropTransform())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholder)
        .into(iv)