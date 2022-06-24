package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.sam43.mindvalleychannels.data.remote.objects.Channel
import com.sam43.mindvalleychannels.databinding.ItemChildDataLandscapeBinding

class LandscapeViewHolder(
    private val binding: ItemChildDataLandscapeBinding
) : BaseViewHolder<Any>(binding) { // mention specific item for 'Any'
    override fun bind(item: Any) = populateView(item, binding)

    private fun populateView(
        item: Any,
        binding: ItemChildDataLandscapeBinding
    ) {
        when (item) {
            is Channel -> {
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.slug
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