package com.sam43.mindvalleychannels.data.remote.objects

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.local.entity.ChannelEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.CourseEntity
import com.sam43.mindvalleychannels.data.local.entity.SeriesEntity
import com.sam43.mindvalleychannels.data.remote.common.CoverAsset
import com.sam43.mindvalleychannels.data.remote.common.IconAsset
import com.sam43.mindvalleychannels.data.remote.common.LatestMedia
import com.sam43.mindvalleychannels.data.remote.common.Sery
@Keep
data class ChannelsResponse(val channels: List<ChannelsItem>) {
    fun toEntity(): List<ChannelsIncludingCourseAndSeries> =
        this.channels.map {
            with(it) {
                ChannelsIncludingCourseAndSeries(
                    ChannelEntity(
                        id,
                        title,
                        mediaCount,
                        iconAsset?.thumbnailUrl ?: iconAsset?.url,
                        coverAsset.url, slug
                    ),
                    this.series.map { apiSeries ->
                        SeriesEntity(
                            apiSeries.title,
                            apiSeries.coverAsset.url,
                            id,
                            title
                        )
                    },
                    this.latestMedia.map { course ->
                        CourseEntity(
                            course.type,
                            course.title,
                            course.coverAsset.url,
                            id,
                            title
                        )
                    }
                )

            }
        }
}

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