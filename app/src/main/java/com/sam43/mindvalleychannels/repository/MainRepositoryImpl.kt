package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.local.AppDB
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.local.mappers.CategoryResponseMapper
import com.sam43.mindvalleychannels.data.local.mappers.ChannelResponseMapper
import com.sam43.mindvalleychannels.data.local.mappers.EpisodeResponseMapper
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLException

@Suppress("BlockingMethodInNonBlockingContext")
class MainRepositoryImpl @Inject constructor(
    private val channelResponseMapper: ChannelResponseMapper,
    private val episodeResponseMapper: EpisodeResponseMapper,
    private val categoryResponseMapper: CategoryResponseMapper,
    private val api: Api,
    private val db: AppDB
    ) : MainRepository {

    override suspend fun getChannelsData(): Flow<EventState<List<ChannelsIncludingCourseAndSeries>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse { api.consumeChannelsData(BuildConfig.ROUTE_CHANNELS) } },
            localDbCall = { db.channelsDao.fetchChannels() },
            localDbObservableCall = { db.channelsDao.fetchChannelsAsFlow() },
            saveNetworkResponse = { db.channelsDao.insertChannelsWithCoursesAndSeries(channelResponseMapper.map(it)) }
        )

    override suspend fun getCategoriesData(): Flow<EventState<List<CategoryEntity>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse { api.consumeCategoriesData(BuildConfig.ROUTE_CATEGORIES) } },
            localDbCall = { db.categoryDao.fetchAllCategories() },
            localDbObservableCall = { db.categoryDao.fetchCategoriesAsFlow() },
            saveNetworkResponse = { db.categoryDao.insertCategories(categoryResponseMapper.map(it)) }
        )

    override suspend fun getNewEpisodesData(): Flow<EventState<List<EpisodeEntity>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse { api.consumeNewEpisodesData(BuildConfig.ROUTE_NEW_EPISODES) } },
            localDbCall = { db.episodeDao.fetchEpisodes() },
            localDbObservableCall = { db.episodeDao.fetchEpisodesAsFlow() },
            saveNetworkResponse = { db.episodeDao.insertEpisodes(episodeResponseMapper.map(it)) }
        )

    private suspend inline fun <reified RT> handleNetworkResponse(
        crossinline block: suspend () -> Response<ResponseData<RT>>
    ): EventState<RT> {
        try {
            val response = block()
            return when {
                response.isSuccessful -> {
                    EventState.Success(response.body()!!.data)
                }
                else -> {
                    throw HttpException(response)
                }
            }
        } catch (e: Exception) {
            return when (e) {
                is ConnectException -> {
                    EventState.Failure(AppConstants.CONNECT_EXCEPTION)
                }
                is UnknownHostException -> {
                    EventState.Failure(AppConstants.UNKNOWN_HOST_EXCEPTION)
                }
                is SocketTimeoutException -> {
                    EventState.Failure(AppConstants.SOCKET_TIME_OUT_EXCEPTION)
                }
                is HttpException -> {
                    return withContext(Dispatchers.IO) {
                        return@withContext EventState.Failure<RT>(
                            e.response()?.errorBody()?.string()
                                ?: AppConstants.UNKNOWN_NETWORK_EXCEPTION
                        )
                    }
                }
                is SSLException -> {
                    EventState.Failure(AppConstants.SSL_EXCEPTION)
                }
                else -> EventState.Failure(AppConstants.UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }

    private inline fun <NetworkResponse, CachedResponse> bindDataFromServer(
        crossinline networkCall: suspend () -> EventState<NetworkResponse>,
        crossinline localDbCall: suspend () -> CachedResponse,
        crossinline localDbObservableCall: () -> Flow<CachedResponse>,
        crossinline saveNetworkResponse: suspend (NetworkResponse) -> Unit
    ): Flow<EventState<CachedResponse>> {
        return flow {
            val cachedResponse = localDbCall()
            emit(EventState.Loading(cachedResponse))
            when (val response = networkCall()) {
                is EventState.Success -> {
                    withContext(Dispatchers.IO) { saveNetworkResponse(response.response) }
                    emitAll(localDbObservableCall().map { EventState.Success(it) })
                }
                is EventState.Failure ->
                    emit(EventState.Failure(response.errorText, cachedResponse))
                is EventState.Loading ->
                    emit(EventState.Loading(cachedResponse))
            }
        }
    }
}