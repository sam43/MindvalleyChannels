package com.sam43.mindvalleychannels.data.remote.model

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sam43.mindvalleychannels.data.remote.model.objects.Category
import com.sam43.mindvalleychannels.data.remote.model.objects.ChannelsItem
import com.sam43.mindvalleychannels.data.remote.model.objects.Media

@Keep
data class ResponseData(
    @SerializedName("data")
    val response: Data
)

@Keep
data class Data(
    @SerializedName("media")
    val media: List<Media>,
    @SerializedName("channels")
    val channelsItems: List<ChannelsItem>,
    @SerializedName("categories")
    val categories: List<Category>
)