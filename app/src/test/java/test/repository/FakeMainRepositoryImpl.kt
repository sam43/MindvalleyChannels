package test.repository

import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.remote.model.ResponseData
import com.sam43.mindvalleychannels.data.remote.service.Api
import com.sam43.mindvalleychannels.data.repository.MainRepository
import test.SetupServiceApiTest
import com.sam43.mindvalleychannels.utils.parser.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class FakeMainRepositoryImpl: SetupServiceApiTest(), MainRepository {
    init {
        mockServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    override suspend fun getChannelsData(): Flow<Resource<ResponseData>> = flow {
        enqueueResponse("test-channel-response.json")
        val channelsInfo = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
        emit(handleResponse(channelsInfo.body()))
    }

    override suspend fun getCategoriesData(): Flow<Resource<ResponseData>> = flow {
        enqueueResponse("test-categories-response.json")
        val categoryInfo = api.consumeResponseData(BuildConfig.ROUTE_CATEGORIES)
        emit(handleResponse(categoryInfo.body()))
    }

    override suspend fun getMediaData(): Flow<Resource<ResponseData>> = flow {
        enqueueResponse("test-new-episode-response.json")
        val episodesInfo = api.consumeResponseData(BuildConfig.ROUTE_NEW_EPISODES)
        emit(handleResponse(episodesInfo.body()))
    }

    private fun handleResponse(data: ResponseData?): Resource<ResponseData> =
        if (data != null) Resource.Success(data) else Resource.Error("Something went wrong",null)
}
