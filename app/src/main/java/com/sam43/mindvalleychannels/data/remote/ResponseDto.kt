package com.sam43.mindvalleychannels.data.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.remote.objects.Category
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsItem
import com.sam43.mindvalleychannels.data.remote.objects.EpisodeItem

@Keep
data class ResponseData<T>(val data: T)