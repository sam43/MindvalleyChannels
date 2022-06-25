package com.sam43.mindvalleychannels.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sam43.mindvalleychannels.R

fun loadImage(iv: ImageView, url: String, placeholder: Int = R.drawable.placeholder) =
    Glide.with(iv.context)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .into(iv)