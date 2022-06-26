package com.sam43.mindvalleychannels.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
class EpisodeEntity(
    val type: String,
    @PrimaryKey val title: String,
    val url: String,
    val channelTitle: String
)