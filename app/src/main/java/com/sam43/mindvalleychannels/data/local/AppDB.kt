package com.sam43.mindvalleychannels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam43.mindvalleychannels.data.local.daos.CategoryDao
import com.sam43.mindvalleychannels.data.local.daos.ChannelsDao
import com.sam43.mindvalleychannels.data.local.daos.MediaDao
import com.sam43.mindvalleychannels.data.local.entity.ChannelsEntity


@Database(
    entities = [ChannelsEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDB: RoomDatabase() {
    abstract val channelsDao: ChannelsDao
    abstract val categoryDao: CategoryDao
    abstract val mediaDao: MediaDao
}