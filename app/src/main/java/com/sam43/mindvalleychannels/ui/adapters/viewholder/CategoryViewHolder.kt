package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.sam43.mindvalleychannels.data.remote.model.objects.Category
import com.sam43.mindvalleychannels.databinding.ItemChildDataCategoryBinding

class CategoryViewHolder(
    private val binding: ItemChildDataCategoryBinding
) : BaseViewHolder<Any>(binding) { // mention specific item for 'Any'
    override fun bind(item: Any) = populateView(item, binding)

    private fun populateView(
        item: Any,
        binding: ItemChildDataCategoryBinding
    ) {
        when (item) {
            is Category -> {
                binding.tvInfo.text = item.name
            }
            else -> {
                val list = arrayListOf(item)
                list.forEach {
                    binding.tvInfo.text = it.toString()
                }
            }
        }
    }
}