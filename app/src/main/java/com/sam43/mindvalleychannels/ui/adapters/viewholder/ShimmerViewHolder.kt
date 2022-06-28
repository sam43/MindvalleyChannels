package com.sam43.mindvalleychannels.ui.adapters.viewholder

import com.sam43.mindvalleychannels.databinding.HorizontalListShimmerBinding

class ShimmerViewHolder (
    binding: HorizontalListShimmerBinding
) : BaseViewHolder<Any>(binding) { // keeping 'Any' here bcz both channel and media can use this VH
    override fun bind(item: Any) = Unit
}