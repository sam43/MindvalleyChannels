package com.sam43.mindvalleychannels.repository

import com.sam43.mindvalleychannels.data.remote.ResponseData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import test.repository.FakeMainRepositoryImpl

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainRepositoryImplTest {
    private val repository: MainRepository by lazy { FakeMainRepositoryImpl() }

    @Test
    fun `test channels flow, return SUCCESS if matches`(): Unit = runBlocking {
        var data: ResponseData? = null
        repository.getChannelsData().collectLatest {
            data = it.data
        }
        val list = data?.response?.channelsItems
        Assert.assertTrue(data != null)
        Assert.assertFalse(data?.response?.channelsItems == null)
        list?.isEmpty()?.let { Assert.assertFalse(it) }
        Assert.assertTrue(list?.last()?.title == "Unlimited Abundance")
        Assert.assertTrue(list?.last()?.mediaCount == 59)
        Assert.assertTrue(list?.last()?.iconAsset == null)
        Assert.assertTrue(list?.last()?.latestMedia?.size == 12)
        Assert.assertFalse(list?.last()?.series?.isNullOrEmpty()!!)
    }

    @Test
    fun `test new episodes flow, return SUCCESS if matches`(): Unit = runBlocking {
        var data: ResponseData? = null
        repository.getMediaData().collectLatest {
            data = it.data
        }
        val list = data?.response?.media
        Assert.assertTrue(data != null)
        Assert.assertFalse(list == null)
        list?.isEmpty()?.let { Assert.assertFalse(it) }
        Assert.assertFalse(list?.last()?.title == "Recorded Live")
        Assert.assertTrue(list?.last()?.title == "Recorded Live Calls: Intellectual Life")
        Assert.assertTrue(list?.last()?.channel?.title == "Lifebook Membership")
        Assert.assertTrue(list?.last()?.coverAsset?.url == "https://assets.mindvalley.com/api/v1/assets/273a5e20-8088-4e94-8f34-6b0241e93962.jpg?transform=w_1080")
        Assert.assertTrue(list?.size == 6)
        Assert.assertTrue(list?.last()?.type == "course")
    }

    @Test
    fun `test categories flow, return SUCCESS if matches`(): Unit = runBlocking {
        var data: ResponseData? = null
        repository.getCategoriesData().collectLatest {
            data = it.data
        }
        val list = data?.response?.categories
        Assert.assertTrue(data != null)
        Assert.assertFalse(list == null)
        list?.isEmpty()?.let { Assert.assertFalse(it) }
        Assert.assertFalse(list?.last()?.name == "Career")
        Assert.assertTrue(list?.first()?.name == "Career")
        Assert.assertTrue(list?.component2()?.name == "Character")
        Assert.assertFalse(list?.component3()?.name == "Parenting")
        Assert.assertFalse(list?.component4()?.name == "Health & Fitness")
        Assert.assertTrue(list?.component5()?.name == "Health & Fitness")
        Assert.assertFalse(list?.component1()?.name == "Spiritual")
    }

    @Test
    fun `test repository method, return FAILED if wrong method called`(): Unit = runBlocking {
        var data: ResponseData? = null
        // trying with wrong api call
        repository.getMediaData().collectLatest {
            data = it.data
        }

        Assert.assertTrue(data != null)
        Assert.assertTrue(data?.response?.channelsItems == null)
        data?.response?.channelsItems?.isEmpty()?.let { Assert.assertTrue(it) }
        AssertionError("Wrong API requested for data")
    }

}