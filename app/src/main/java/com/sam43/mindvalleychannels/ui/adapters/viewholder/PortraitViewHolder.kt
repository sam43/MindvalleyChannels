package com.sam43.mindvalleychannels.ui.adapters.viewholder

import androidx.core.view.isVisible
import com.sam43.mindvalleychannels.data.remote.model.common.LatestMedia
import com.sam43.mindvalleychannels.data.remote.model.common.Sery
import com.sam43.mindvalleychannels.data.remote.model.objects.Media
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
            is LatestMedia -> {
                binding.tvSubInfo.isVisible = false
                binding.tvInfo.text = item.title
                loadImage(binding.imgCover, item.coverAsset.url)
            }
            is Sery -> {
                binding.tvSubInfo.isVisible = false
                binding.tvInfo.text = item.title
                loadImage(binding.imgCover, item.coverAsset.url)
            }
            is Media -> {
                binding.tvSubInfo.isVisible = true
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.channel.title
                loadImage(binding.imgCover, item.coverAsset.url)
            }
            else -> {
//                val list = arrayListOf(item)
//                list.forEach {
//                    binding.tvInfo.text = it.toString()
//                }
            }
        }
    }
}