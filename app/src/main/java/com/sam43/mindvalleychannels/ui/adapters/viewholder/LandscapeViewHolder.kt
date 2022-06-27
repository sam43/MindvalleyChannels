package com.sam43.mindvalleychannels.ui.adapters.viewholder

import androidx.core.view.isVisible
import com.sam43.mindvalleychannels.data.local.entity.CourseEntity
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.local.entity.SeriesEntity
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