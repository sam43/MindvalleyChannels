package com.sam43.mindvalleychannels.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ChannelEntity::class,
        parentColumns = ["id", "title"],
        childColumns = ["channelId", "channelTitle"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class CourseEntity(
    val type: String,
    val title: String,
    val url: String,
    val channelId: String,
    val channelTitle: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)