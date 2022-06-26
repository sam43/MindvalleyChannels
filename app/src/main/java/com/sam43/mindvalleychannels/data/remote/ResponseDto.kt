package com.sam43.mindvalleychannels.data.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.remote.objects.Category
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsItem
import com.sam43.mindvalleychannels.data.remote.objects.EpisodeItem

@Keep
data class ResponseData<T>(val data: T)

@Deprecated("Not Using")
@Keep
data class ResponseDataX(
    @SerializedName("data")
    val response: Data
)

@Deprecated("Not Using")
@Keep
data class Data(
    @SerializedName("media")
    val media: List<EpisodeItem>,
    @SerializedName("channels")
    val channelsItems: List<ChannelsItem>,
    @SerializedName("categories")
    val categories: List<Category>
)