package com.sam43.mindvalleychannels.data.local.mappers

import android.util.Log
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.remote.objects.CategoryResponse
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import javax.inject.Inject

class CategoryResponseMapper @Inject constructor() :
    Mapper<CategoryResponse, List<CategoryEntity>> {
    override suspend fun map(from: CategoryResponse): List<CategoryEntity> {
        Log.d(TAG, "map() called with: from = $from")
        return if (!from.category.isNullOrEmpty()) {
            from.category.map {
                with(it) {
                    CategoryEntity(name)
                }
            }
        } else emptyList()
    }

}