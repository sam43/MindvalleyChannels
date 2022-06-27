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
import com.sam43.mindvalleychannels.network.Api
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class MainRepositoryImpl @Inject constructor(
    private val channelResponseMapper: ChannelResponseMapper,
    private val episodeResponseMapper: EpisodeResponseMapper,
    private val categoryResponseMapper: CategoryResponseMapper,
    private val api: Api,
    private val db: AppDB
) : AbsRepository(), MainRepository {

    override fun getChannelsData(): Flow<EventState<List<ChannelsIncludingCourseAndSeries>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse { api.consumeChannelsData(BuildConfig.ROUTE_CHANNELS) } },
            localDbCall = { db.channelsDao.fetchChannels() },
            localDbObservableCall = { db.channelsDao.fetchChannelsAsFlow() },
            saveNetworkResponse = {
                db.channelsDao.insertChannelsWithCoursesAndSeries(channelResponseMapper.map(it))
            }
        )

    override fun getCategoriesData(): Flow<EventState<List<CategoryEntity>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse {
                api.consumeCategoriesData(BuildConfig.ROUTE_CATEGORIES)
            } },
            localDbCall = {
                db.categoryDao.fetchAllCategories() },
            localDbObservableCall = {
                db.categoryDao.fetchCategoriesAsFlow() },
            saveNetworkResponse = {
                db.categoryDao.insertCategories(categoryResponseMapper.map(it)) }
        )

    override fun getNewEpisodesData(): Flow<EventState<List<EpisodeEntity>>> =
        bindDataFromServer(
            networkCall = { handleNetworkResponse { api.consumeNewEpisodesData(BuildConfig.ROUTE_NEW_EPISODES) } },
            localDbCall = { db.episodeDao.fetchEpisodes() },
            localDbObservableCall = { db.episodeDao.fetchEpisodesAsFlow() },
            saveNetworkResponse = { db.episodeDao.insertEpisodes(episodeResponseMapper.map(it)) }
        )
}