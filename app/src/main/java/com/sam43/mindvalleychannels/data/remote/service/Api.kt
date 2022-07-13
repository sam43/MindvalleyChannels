package com.sam43.mindvalleychannels.data.remote.service

import com.sam43.mindvalleychannels.data.remote.model.PlaceHolderDataItem
import com.sam43.mindvalleychannels.data.remote.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("posts")
    suspend fun consumePlaceholderData(): Response<List<PlaceHolderDataItem>>

    @GET("raw/{route}")
    suspend fun consumeResponseData(@Path("route") route: String): Response<ResponseData>
}