package com.sam43.mindvalleychannels.repository

import android.util.Log
import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.local.daos.CategoryDao
import com.sam43.mindvalleychannels.data.local.daos.ChannelsDao
import com.sam43.mindvalleychannels.data.local.daos.MediaDao
import com.sam43.mindvalleychannels.data.remote.objects.Category
import com.sam43.mindvalleychannels.data.remote.objects.Channel
import com.sam43.mindvalleychannels.data.remote.objects.Media
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: Api) : MainRepository {
    @Inject
    lateinit var channelsDao: ChannelsDao
    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var mediaDao: MediaDao

    override fun getChannelsData(): Flow<Resource<Channel>> = flow {
        val channelsInfo = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
    }

    override fun getCategoriesData(): Flow<Resource<Category>> = flow {
        val categoryInfo = api.consumeResponseData(BuildConfig.ROUTE_CATEGORIES)
    }

    override fun getMediaData(): Flow<Resource<Media>> = flow {
        val newEpisodesInfo = api.consumeResponseData(BuildConfig.ROUTE_NEW_EPISODES)
    }
}