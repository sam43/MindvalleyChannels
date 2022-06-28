package com.sam43.mindvalleychannels.data.remote

sealed class EventView {
    class Success<T>(val response: List<T>?) : EventView()
    class Failure(val errorText: String) : EventView()
    object Loading : EventView()
}
