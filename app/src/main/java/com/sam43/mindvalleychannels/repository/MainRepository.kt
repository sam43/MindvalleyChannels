package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
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
    suspend fun getChannelsData(): Flow<EventState<ChannelsIncludingCourseAndSeries>>
    suspend fun getCategoriesData(): Flow<EventState<CategoryResponse>>
    suspend fun getNewEpisodesData(): Flow<EventState<EpisodesResponse>>
}