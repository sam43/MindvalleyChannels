package com.sam43.mindvalleychannels.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "channels")
data class ChannelsEntity(
    @PrimaryKey val id: Int? = null,
    val channel: String? = null,
)
