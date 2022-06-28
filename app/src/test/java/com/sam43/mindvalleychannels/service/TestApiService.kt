package com.sam43.mindvalleychannels.service

import com.sam43.mindvalleychannels.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TestApiService: SetupServiceApiTest() {

    @Test
    fun `test channels api from server request, if parsed correctly`() = runBlocking {
        enqueueResponse("test-channel-response.json")
        val response = api.consumeResponseData(BuildConfig.ROUTE_CHANNELS)
        //test request
        val request = mockServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/raw/Xt12uVhM", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        //test response
        val channels = response.body()?.response?.channelsItems
        Assert.assertEquals(channels?.size, 9)
        Assert.assertEquals(channels?.first()?.series?.size, 0)
        Assert.assertEquals(channels?.get(4)?.series?.size, 4)
        Assert.assertEquals(channels?.first()?.latestMedia?.size, 12)
        Assert.assertEquals(channels?.first()?.latestMedia?.get(0)?.type, "course")
        Assert.assertEquals(channels?.first()?.latestMedia?.get(3)?.title, "What To Eat For A Genius Brain")
        Assert.assertEquals(channels?.first()?.latestMedia?.get(3)?.coverAsset?.url, "https://assets.mindvalley.com/api/v1/assets/bff42af7-5a7e-46fb-bcda-7eb7dc3fb342.jpg")
        Assert.assertEquals(channels?.first()?.mediaCount, 98)
        Assert.assertNotEquals(channels?.first()?.mediaCount, 28)
        Assert.assertEquals(
            channels?.first()?.iconAsset?.thumbnailUrl,
            "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png"
        )
        Assert.assertEquals(
            channels?.first()?.coverAsset?.url,
            "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )
    }

    @Test
    fun `test new episodes api from server request, if parsed correctly`() =
        runBlocking {
            enqueueResponse("test-new-episode-response.json")
            val response = api.consumeResponseData(BuildConfig.ROUTE_NEW_EPISODES)
            //test request
            val request = mockServer.takeRequest()
            Assert.assertEquals("GET", request.method)
            Assert.assertEquals("/raw/z5AExTtw", request.path)
            Assert.assertEquals(response.isSuccessful, true)

            //test response
            val newEpisodes = response.body()?.response?.media
            Assert.assertEquals(newEpisodes?.size, 6)
            Assert.assertEquals(newEpisodes?.first()?.channel?.title, "Little Humans")
            Assert.assertEquals(newEpisodes?.first()?.type, "course")
            Assert.assertEquals(newEpisodes?.first()?.title, "Conscious Parenting")
            Assert.assertEquals(
                newEpisodes?.first()?.coverAsset?.url,
                "https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080"
            )


        }


    @Test
    fun `test categories api from server request, if parsed correctly`() = runBlocking {
        enqueueResponse("test-categories-response.json")
        val response = api.consumeResponseData(BuildConfig.ROUTE_CATEGORIES)
        //test request
        val request = mockServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/raw/A0CgArX3", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        //test response
        val categories = response.body()?.response?.categories
        Assert.assertEquals(categories?.size, 12)
        Assert.assertNotEquals(categories?.size, 6)
        Assert.assertNotEquals(categories?.first()?.name, "Careers")
        Assert.assertEquals(categories?.first()?.name, "Career")
        Assert.assertEquals(categories?.component2()?.name, "Character")
        Assert.assertEquals(categories?.component3()?.name, "Emotional")
        Assert.assertNotEquals(categories?.component4()?.name, "Financial Time")
        Assert.assertEquals(categories?.component4()?.name, "Financial")

    }

}