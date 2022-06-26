package com.sam43.mindvalleychannels.data.local.mappers

import com.sam43.mindvalleychannels.data.local.entity.ChannelEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.CourseEntity
import com.sam43.mindvalleychannels.data.local.entity.SeriesEntity
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsResponse
import javax.inject.Inject

class ChannelResponseMapper @Inject constructor() :
    Mapper<ChannelsResponse, List<ChannelsIncludingCourseAndSeries>> {
    override suspend fun map(from: ChannelsResponse): List<ChannelsIncludingCourseAndSeries> =
        from.channels.map {
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