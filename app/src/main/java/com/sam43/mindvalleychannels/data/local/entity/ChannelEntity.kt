package com.sam43.mindvalleychannels.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["id", "title"], tableName = "channel")
data class ChannelEntity(
    val id: String = "",
    val title: String,
    val mediaCount: Int,
    val iconAssetUrl: String?,
    val coverAssetUrl: String?,
    val slug: String?
)