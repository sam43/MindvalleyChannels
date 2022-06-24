package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.sam43.mindvalleychannels.data.remote.objects.Channel
import com.sam43.mindvalleychannels.data.remote.objects.Media
import com.sam43.mindvalleychannels.databinding.ItemChildDataPortraitBinding

class PortraitViewHolder(
    private val binding: ItemChildDataPortraitBinding
) : BaseViewHolder<Any>(binding) { // keeping 'Any' here bcz both channel and media can use this VH
    override fun bind(item: Any) = populateView(item, binding)

    private fun populateView(
        item: Any,
        binding: ItemChildDataPortraitBinding
    ) {
        when (item) {
            is Channel -> {
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.slug
            }
            is Media -> {
                binding.tvInfo.text = item.title
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