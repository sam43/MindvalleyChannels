package com.sam43.mindvalleychannels.data.remote.model.objects


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.remote.model.common.CoverAsset

@Keep
data class Media(
    @SerializedName("channel")
    val channel: Channel,
    @SerializedName("coverAsset")
    val coverAsset: CoverAsset,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)

@Keep
data class Channel(
    @SerializedName("title")
    val title: String
)

