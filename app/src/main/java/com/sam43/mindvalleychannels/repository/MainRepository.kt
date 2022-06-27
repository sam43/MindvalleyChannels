package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.data.remote.objects.CategoryResponse
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsResponse
import com.sam43.mindvalleychannels.data.remote.objects.EpisodesResponse
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

interface MainRepository {
    fun getChannelsData(): Flow<EventState<List<ChannelsIncludingCourseAndSeries>>>
    fun getCategoriesData(): Flow<EventState<List<CategoryEntity>>>
    fun getNewEpisodesData(): Flow<EventState<List<EpisodeEntity>>>
}