package com.sam43.mindvalleychannels.data.remote

sealed class EventState<T> {
    data class Loading<T>(val response: T?) : EventState<T>()
    data class Success<T>(val response: T) : EventState<T>()
    data class Failure<T>(val errorText: String, val data: T? = null) : EventState<T>()
}
