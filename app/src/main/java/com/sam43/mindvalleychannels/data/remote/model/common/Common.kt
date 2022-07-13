package com.sam43.mindvalleychannels.data.remote.model.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CoverAsset(
    @SerializedName("url")
    val url: String
)

@Keep
data class IconAsset(
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,
    @SerializedName("url")
    val url: String? = null
)

@Keep
data class LatestMedia(
    @SerializedName("coverAsset")
    val coverAsset: CoverAsset,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)

@Keep
data class Sery(
    @SerializedName("coverAsset")
    val coverAsset: CoverAsset,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)