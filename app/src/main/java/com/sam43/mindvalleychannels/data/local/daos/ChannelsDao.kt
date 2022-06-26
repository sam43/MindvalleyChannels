package com.sam43.mindvalleychannels.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam43.mindvalleychannels.data.local.entity.ResponseEntity

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(info: ResponseEntity)

    @Query("SELECT * FROM response")
    suspend fun getChannelsOffline(): ResponseEntity
}