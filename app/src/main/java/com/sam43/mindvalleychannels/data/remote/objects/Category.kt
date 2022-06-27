package com.sam43.mindvalleychannels.data.remote.objects

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity

@Keep
data class CategoryResponse(val category: List<Category>) {
    fun toEntity(): List<CategoryEntity> =
        this.category.map {
            CategoryEntity(name = it.name)
        }
}

@Keep
data class Category(
    @SerializedName("name")
    val name: String
)