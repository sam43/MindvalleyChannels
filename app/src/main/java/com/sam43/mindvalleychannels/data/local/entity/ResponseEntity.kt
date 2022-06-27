package com.sam43.mindvalleychannels.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sam43.mindvalleychannels.data.local.typeconverter.Converters
import com.sam43.mindvalleychannels.data.remote.Data

@Keep
@Entity(tableName = "response")
@TypeConverters(Converters::class)
data class ResponseEntity(
    @PrimaryKey val id: Int? = null,
    val response: Data
)
