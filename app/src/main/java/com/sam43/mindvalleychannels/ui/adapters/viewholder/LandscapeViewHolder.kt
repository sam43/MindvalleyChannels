package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.sam43.mindvalleychannels.data.remote.objects.ChannelsItem
import com.sam43.mindvalleychannels.databinding.ItemChildDataLandscapeBinding
import com.sam43.mindvalleychannels.utils.loadImage

class LandscapeViewHolder(
    private val binding: ItemChildDataLandscapeBinding
) : BaseViewHolder<Any>(binding) { // mention specific item for 'Any'
    override fun bind(item: Any) = populateView(item, binding)

    private fun populateView(
        item: Any,
        binding: ItemChildDataLandscapeBinding
    ) {
        when (item) {
            is ChannelsItem -> {
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.slug
                loadImage(binding.imgCover, item.coverAsset.url)
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