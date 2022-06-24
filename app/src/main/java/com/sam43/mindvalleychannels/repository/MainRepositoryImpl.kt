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
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: Api) : MainRepository {
    @Inject
    lateinit var channelsDao: ChannelsDao
    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var mediaDao: MediaDao

    override fun getChannelsData(): Flow<Resource<Channel>> = flow {
        //val abc = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
        val abc = api.consumePlaceholderData()
        Log.d(TAG, "getChannelsData() called response : ${abc.body().toString()}")
    }

    override fun getCategoriesData(): Flow<Resource<Category>> = flow {  }

    override fun getMediaData(): Flow<Resource<Media>> = flow {  }
}