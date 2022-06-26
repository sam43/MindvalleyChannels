package com.sam43.mindvalleychannels.data.local.daos

import androidx.room.*
import com.sam43.mindvalleychannels.data.local.entity.ChannelEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.CourseEntity
import com.sam43.mindvalleychannels.data.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelsDao {
    @Query("SELECT * FROM channel")
    fun fetchChannelsAsFlow(): Flow<List<ChannelsIncludingCourseAndSeries>>

    @Query("SELECT * FROM channel")
    suspend fun fetchChannels(): List<ChannelsIncludingCourseAndSeries>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: ChannelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SeriesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("Delete from channel where title = :title and id = :id")
    suspend fun deleteChannel(id: String, title: String)

    @Transaction
    suspend fun insertChannelsWithCoursesAndSeries(channels: List<ChannelsIncludingCourseAndSeries>) {
        for (channel in channels) {
            deleteChannel(channel.channel.id, channel.channel.title)
            insertChannel(channel.channel)
            insertCourses(channel.courses)
            insertSeries(channel.series)
        }
    }
}