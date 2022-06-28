package com.sam43.mindvalleychannels.utils.parser

sealed class ResponseEvent {
    class SuccessResponse<T>(val response: T?) : ResponseEvent()
    class Failure(val errorText: String) : ResponseEvent()
    class ConnectionFailure(val errorText: String) : ResponseEvent()
    object Loading : ResponseEvent()
}
