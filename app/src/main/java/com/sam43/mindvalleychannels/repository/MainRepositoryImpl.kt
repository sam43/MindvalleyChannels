package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.data.remote.objects.CategoryResponse
import com.sam43.mindvalleychannels.data.remote.objects.EpisodesResponse
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

class MainRepositoryImpl @Inject constructor(private val api: Api) : MainRepository {

    override suspend fun getChannelsData(): Flow<EventState<ChannelsIncludingCourseAndSeries>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesData(): Flow<EventState<CategoryResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewEpisodesData(): Flow<EventState<EpisodesResponse>> {
        TODO("Not yet implemented")
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend inline fun <reified RT> processNetworkResponse(
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

    private inline fun <NetworkResponse, CachedResponse> bindLocalAndRemoteCalls(
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
                is EventState.Failure -> {
                    emit(EventState.Failure(response.errorText, cachedResponse))
                }
            }
        }
    }
}