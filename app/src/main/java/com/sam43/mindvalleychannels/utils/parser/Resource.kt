package com.sam43.mindvalleychannels.utils.parser

sealed class Resource<T>(val data: T?, val message: String?) {
    class Loading<T>(data: T? = null): Resource<T>(data, null)
    class Success<T>(data: T) : Resource<T>(data, null)
    class NoInternet<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}