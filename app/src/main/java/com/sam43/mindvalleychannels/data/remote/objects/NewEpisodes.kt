package com.sam43.mindvalleychannels.data.remote.objects


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.common.CoverAsset

@Keep
data class EpisodesResponse(val media: List<EpisodeItem>) {
    fun toEntity(): List<EpisodeEntity> =
        this.media.map {
            with(it) {
                EpisodeEntity(
                    type,
                    title,
                    url = coverAsset.url,
                    channelTitle = channel.title
                )
            }
        }
}

@Keep
data class EpisodeItem(
    val type: String,
    val title: String,
    val coverAsset: CoverAsset,
    val channel: ChannelsItem
)

