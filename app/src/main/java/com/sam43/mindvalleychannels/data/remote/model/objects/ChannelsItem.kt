package com.sam43.mindvalleychannels.data.remote.model.objects

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.remote.model.common.CoverAsset
import com.sam43.mindvalleychannels.data.remote.model.common.IconAsset
import com.sam43.mindvalleychannels.data.remote.model.common.LatestMedia
import com.sam43.mindvalleychannels.data.remote.model.common.Sery
@Keep
data class ChannelsItem(
    @SerializedName("coverAsset")
    val coverAsset: CoverAsset,
    @SerializedName("iconAsset")
    val iconAsset: IconAsset?,
    @SerializedName("id")
    val id: String,
    @SerializedName("latestMedia")
    val latestMedia: List<LatestMedia>,
    @SerializedName("mediaCount")
    val mediaCount: Int,
    @SerializedName("series")
    val series: List<Sery>,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String
)