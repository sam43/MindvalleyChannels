package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.bumptech.glide.Glide
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsItem
import com.sam43.mindvalleychannels.data.remote.objects.Media
import com.sam43.mindvalleychannels.databinding.ItemChildDataPortraitBinding
import com.sam43.mindvalleychannels.utils.loadImage

class PortraitViewHolder(
    private val binding: ItemChildDataPortraitBinding
) : BaseViewHolder<Any>(binding) { // keeping 'Any' here bcz both channel and media can use this VH
    override fun bind(item: Any) = populateView(item, binding)

    private fun populateView(
        item: Any,
        binding: ItemChildDataPortraitBinding
    ) {
        when (item) {
            is ChannelsItem -> {
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.slug
                loadImage(binding.imgCover, item.coverAsset.url)
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