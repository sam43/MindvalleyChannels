package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: Api) : MainRepository {

    override suspend fun getChannelsData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val channelsInfo = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
        val data = getResponseData(channelsInfo)
        emit(emitResponse(data))
    }

    override suspend fun getCategoriesData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val categoryInfo = api.consumeResponseData(BuildConfig.ROUTE_CATEGORIES)
        val data = getResponseData(categoryInfo)
        emit(emitResponse(data))
    }

    override suspend fun getMediaData(): Flow<Resource<ResponseData>> = flow {
        emit(Resource.Loading())
        val newEpisodesInfo = api.consumeResponseData(BuildConfig.ROUTE_NEW_EPISODES)
        val data = getResponseData(newEpisodesInfo)
        emit(emitResponse(data))
    }

    private fun emitResponse(data: ResponseData?): Resource<ResponseData> {
        return try {
            if (data != null) Resource.Success(data) else Resource.Error("Something Wrong",null)
        } catch (e: HttpException) {
            Resource.Error(
                message = "Oops, Some error occurred while parsing the response!",
                data = data
            )
        } catch (e: IOException) {
            Resource.NoInternet(
                message = "Couldn't reach server, check your internet connection.",
                data = data
            )
        }
    }

    private fun getResponseData(response: Response<ResponseData>): ResponseData? =
        if (response.isSuccessful)
            response.body()
        else null

}