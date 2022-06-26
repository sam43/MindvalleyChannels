package com.sam43.mindvalleychannels.data.remote.objects


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.remote.common.CoverAsset

@Keep
data class EpisodesResponse(val media: List<EpisodeItem>)

@Keep
data class EpisodeItem(
    val type: String,
    val title: String,
    val coverAsset: CoverAsset,
    val channel: Channel
)

@Keep
data class Channel(val title: String)

