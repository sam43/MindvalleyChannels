package com.sam43.mindvalleychannels.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChannelsIncludingCourseAndSeries(
    @Embedded
    val channel: ChannelEntity,
    @Relation(parentColumn = "title", entityColumn = "channelTitle")
    val series: List<SeriesEntity>,
    @Relation(parentColumn = "title", entityColumn = "channelTitle")
    val courses: List<CourseEntity>
) {
    fun isSeries() = series.isEmpty().not()
}
