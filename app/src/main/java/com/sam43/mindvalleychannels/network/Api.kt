package com.sam43.mindvalleychannels.network

import com.sam43.mindvalleychannels.data.remote.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("raw/{route}")
    fun consumeResponseData(@Path("route") route: String): Response<ResponseData>
}