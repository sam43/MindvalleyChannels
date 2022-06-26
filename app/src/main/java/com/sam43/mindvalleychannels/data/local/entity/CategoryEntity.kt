package com.sam43.mindvalleychannels.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(@PrimaryKey val name: String)