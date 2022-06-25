package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getChannelsData(): Flow<Resource<ResponseData>>
    suspend fun getCategoriesData(): Flow<Resource<ResponseData>>
    suspend fun getMediaData(): Flow<Resource<ResponseData>>
}