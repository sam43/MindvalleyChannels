package com.sam43.mindvalleychannels.data.local.mappers

import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.remote.objects.CategoryResponse
import javax.inject.Inject

class CategoryResponseMapper @Inject constructor() :
    Mapper<CategoryResponse, List<CategoryEntity>> {
    override suspend fun map(from: CategoryResponse): List<CategoryEntity> =
        from.category.map {
            with(it) {
                CategoryEntity(name)
            }
        }
}