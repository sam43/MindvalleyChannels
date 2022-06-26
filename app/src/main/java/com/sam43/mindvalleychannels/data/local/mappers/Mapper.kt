package com.sam43.mindvalleychannels.data.local.mappers

interface Mapper<T, U> {
    suspend fun map(from: T): U
}
