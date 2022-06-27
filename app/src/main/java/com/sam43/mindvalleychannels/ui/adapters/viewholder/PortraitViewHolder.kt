package com.sam43.mindvalleychannels.ui.adapters.viewholder

import androidx.core.view.isVisible
import com.sam43.mindvalleychannels.data.local.entity.CourseEntity
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.local.entity.SeriesEntity
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
            is CourseEntity -> {
                binding.tvSubInfo.isVisible = false
                binding.tvInfo.text = item.title
                loadImage(binding.imgCover, item.url)
            }
            is SeriesEntity -> {
                binding.tvSubInfo.isVisible = false
                binding.tvInfo.text = item.title
                loadImage(binding.imgCover, item.url)
            }
            is EpisodeEntity -> {
                binding.tvSubInfo.isVisible = true
                binding.tvInfo.text = item.title
                binding.tvSubInfo.text = item.channelTitle
                loadImage(binding.imgCover, item.url)
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