package com.sam43.mindvalleychannels.data.repository

import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.remote.model.ResponseData
import com.sam43.mindvalleychannels.data.remote.service.Api
import com.sam43.mindvalleychannels.utils.NetworkConstants
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named
import javax.net.ssl.SSLException

class MainRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val api: Api,
    @Named("hasNetwork") private val hasNetwork: Boolean
    ) : MainRepository {

    override suspend fun getChannelsData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val channelsInfo = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
        val data = getResponseData(channelsInfo)
        emit(handleResponse(data))
        if (!hasNetwork) Resource.NoInternet(NetworkConstants.CONNECT_EXCEPTION, data)
    }

    override suspend fun getCategoriesData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val categoryInfo = api.consumeResponseData(BuildConfig.ROUTE_CATEGORIES)
        val data = getResponseData(categoryInfo)
        emit(handleResponse(data))
        if (!hasNetwork) Resource.NoInternet(NetworkConstants.CONNECT_EXCEPTION, data)
    }

    override suspend fun getMediaData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val newEpisodesInfo = api.consumeResponseData(BuildConfig.ROUTE_NEW_EPISODES)
        val data = getResponseData(newEpisodesInfo)
        emit(handleResponse(data))
        if (!hasNetwork) Resource.NoInternet(NetworkConstants.CONNECT_EXCEPTION, data)
    }

    private fun handleResponse(data: ResponseData?): Resource<ResponseData> =
        if (data != null) Resource.Success(data) else Resource.Error("Something went wrong",null)

    private fun handleExceptions(e: Exception): Resource<ResponseData> = when (e) {
            is ConnectException -> {
                Resource.Error(NetworkConstants.CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                Resource.Error(NetworkConstants.UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                Resource.Error(NetworkConstants.SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                Resource.Error(
                    e.response()?.errorBody()?.string()
                        ?: NetworkConstants.UNKNOWN_NETWORK_EXCEPTION
                )
            }
            is SSLException -> {
                Resource.Error(NetworkConstants.SSL_EXCEPTION)
            }
            else ->
                Resource.Error(NetworkConstants.UNKNOWN_NETWORK_EXCEPTION)
        }

    private fun getResponseData(response: Response<ResponseData>): ResponseData? =
        if (response.isSuccessful)
            response.body()
        else {
            parseError(response)
            null
        }
    private fun parseError(response: Response<*>) {
        val converter: Converter<ResponseBody, Exception> = retrofit
            .responseBodyConverter(Exception::class.java, arrayOfNulls<Annotation>(0))
        try {
            response.errorBody()?.let { converter.convert(it) }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }
}