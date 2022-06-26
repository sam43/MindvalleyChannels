package com.sam43.mindvalleychannels.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun fetchCategoriesAsFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category")
    suspend fun fetchAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)
}
