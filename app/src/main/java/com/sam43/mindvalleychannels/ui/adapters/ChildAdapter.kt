package com.sam43.mindvalleychannels.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam43.mindvalleychannels.databinding.ItemChildDataCategoryBinding
import com.sam43.mindvalleychannels.databinding.ItemChildDataLandscapeBinding
import com.sam43.mindvalleychannels.databinding.ItemChildDataPortraitBinding
import com.sam43.mindvalleychannels.ui.adapters.viewholder.*
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_GRID_CATEGORY
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_RAIL_LANDSCAPE
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_RAIL_PORTRAIT

@SuppressLint("NotifyDataSetChanged")
class ChildAdapter: RecyclerView.Adapter<BaseViewHolder<Any>>() {

    private var currentBindViewType = TYPE_RAIL_PORTRAIT
    private var type = ViewType.COURSE.name
    private var items = listOf<Any>()

    fun setItems(list: List<Any>, type: String) {
        this.items = list
        this.type = type
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        return when (viewType) {
            TYPE_RAIL_PORTRAIT ->
                PortraitViewHolder(
                    ItemChildDataPortraitBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            // for landscape item design
            TYPE_RAIL_LANDSCAPE ->
                LandscapeViewHolder(
                    ItemChildDataLandscapeBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                ) as BaseViewHolder<Any>
            TYPE_GRID_CATEGORY ->
                CategoryViewHolder(
                    ItemChildDataCategoryBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                ) as BaseViewHolder<Any>
            else ->
                throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        currentBindViewType = when (type) {
            ViewType.SERIES.name -> TYPE_RAIL_LANDSCAPE
            ViewType.COURSE.name -> TYPE_RAIL_PORTRAIT
            ViewType.CATEGORY.name -> TYPE_GRID_CATEGORY
            else -> TYPE_RAIL_PORTRAIT
        }
        return currentBindViewType
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        holder.bind(items[position])
    }
}
