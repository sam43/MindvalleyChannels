package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.EventState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getChannelsData(): Flow<EventState<List<ChannelsIncludingCourseAndSeries>>>
    suspend fun getCategoriesData(): Flow<EventState<List<CategoryEntity>>>
    suspend fun getNewEpisodesData(): Flow<EventState<List<EpisodeEntity>>>
}