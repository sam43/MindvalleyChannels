package com.sam43.mindvalleychannels.network

import com.sam43.mindvalleychannels.data.remote.ResponseData
import com.sam43.mindvalleychannels.data.remote.ResponseDataX
import com.sam43.mindvalleychannels.data.remote.objects.CategoryResponse
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsResponse
import com.sam43.mindvalleychannels.data.remote.objects.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @Deprecated("Not in Use")
    @GET("raw/{route}")
    suspend fun consumeResponseData(@Path("route") route: String): Response<ResponseDataX>

    @GET("raw/{route}")
    suspend fun consumeNewEpisodesData(@Path("route") route: String): Response<ResponseData<EpisodesResponse>>

    @GET("raw/{route}")
    suspend fun consumeCategoriesData(@Path("route") route: String): Response<ResponseData<CategoryResponse>>

    @GET("raw/{route}")
    suspend fun consumeChannelsData(@Path("route") route: String): Response<ResponseData<ChannelsResponse>>
}