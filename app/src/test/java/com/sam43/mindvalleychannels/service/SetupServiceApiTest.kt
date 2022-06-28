package com.sam43.mindvalleychannels.service

import androidx.annotation.CallSuper
import com.sam43.mindvalleychannels.BaseApiTest
import com.sam43.mindvalleychannels.network.Api
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
abstract class SetupServiceApiTest: BaseApiTest() {

    @Mock
    lateinit var api: Api
    @Mock
    lateinit var mockServer: MockWebServer

    @CallSuper
    @Before
    open fun setUp() {
        mockServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @CallSuper
    @After
    open fun tearDown() {
        mockServer.shutdown()
    }

    protected fun enqueueResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap(),
        socketPolicy: SocketPolicy = SocketPolicy.KEEP_OPEN
    ) {
        val mockResponse = MockResponse().apply {
            this.socketPolicy = socketPolicy
            for ((key, value) in headers) {
                addHeader(key, value)
            }
        }

        mockServer.enqueue(
            mockResponse
                .setBody(getJsonStringFromFile(fileName))
        )
    }
}