package com.sam43.mindvalleychannels.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episode")
    fun fetchEpisodesAsFlow(): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episode")
    suspend fun fetchEpisodes(): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(newEpisodes: List<EpisodeEntity>)
}
