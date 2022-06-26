package com.sam43.mindvalleychannels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam43.mindvalleychannels.data.local.daos.CategoryDao
import com.sam43.mindvalleychannels.data.local.daos.ChannelsDao
import com.sam43.mindvalleychannels.data.local.daos.EpisodeDao
import com.sam43.mindvalleychannels.data.local.entity.*


@Database(
    entities = [CategoryEntity::class, CourseEntity::class, ChannelEntity::class, EpisodeEntity::class, SeriesEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDB: RoomDatabase() {
    abstract val channelsDao: ChannelsDao
    abstract val categoryDao: CategoryDao
    abstract val episodeDao: EpisodeDao
}