package com.sam43.mindvalleychannels.data.remote.model
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

@Keep
data class PlaceHolderDataItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)
