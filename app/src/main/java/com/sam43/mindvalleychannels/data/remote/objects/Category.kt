package com.sam43.mindvalleychannels.data.remote.objects

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Category(
    @SerializedName("name")
    val name: String
)
