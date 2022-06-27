package com.sam43.mindvalleychannels.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.sam43.mindvalleychannels.R

fun loadImage(iv: ImageView, url: String?, placeholder: Int = R.drawable.placeholder) =
    Glide.with(iv.context)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .into(iv)

fun loadImageCircular(iv: ImageView, url: String?, placeholder: Int = R.drawable.ic_icon_asset_circular) =
    Glide.with(iv.context)
        .load(url)
        .dontAnimate()
        .apply(RequestOptions.circleCropTransform())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .into(iv)

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}