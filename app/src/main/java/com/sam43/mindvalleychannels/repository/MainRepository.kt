package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.remote.objects.Category
import com.sam43.mindvalleychannels.data.remote.objects.Channel
import com.sam43.mindvalleychannels.data.remote.objects.Media
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getChannelsData(): Flow<Resource<Channel>>
    fun getCategoriesData(): Flow<Resource<Category>>
    fun getMediaData(): Flow<Resource<Media>>
}