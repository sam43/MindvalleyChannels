package com.sam43.mindvalleychannels.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.data.remote.common.LatestMedia
import com.sam43.mindvalleychannels.data.remote.common.Sery
import com.sam43.mindvalleychannels.data.remote.objects.ChannelsItem
import com.sam43.mindvalleychannels.databinding.ItemParentDataBinding
import com.sam43.mindvalleychannels.ui.adapters.viewholder.ViewType
import com.sam43.mindvalleychannels.ui.model.TitledList
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_GRID_CATEGORY
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_RAIL_LANDSCAPE
import com.sam43.mindvalleychannels.utils.AppConstants.TYPE_RAIL_PORTRAIT
import com.sam43.mindvalleychannels.utils.AppConstants.isListOfType
import com.sam43.mindvalleychannels.utils.AppConstants.isMutableListOfType
import com.sam43.mindvalleychannels.utils.loadImage
import com.sam43.mindvalleychannels.utils.loadImageCircular

@SuppressLint("NotifyDataSetChanged")
class ParentAdapter(private val scrollStateHolder: ScrollStateHolder) :
    RecyclerView.Adapter<ParentAdapter.VH>() {
    private var items = listOf<TitledList>()


    fun setItems(list: List<TitledList>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemParentDataBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), scrollStateHolder
        )
    }

    private fun setLayoutType(vh: VH, position: Int) {
        if (items.isNullOrEmpty()) return
        val layoutManagerType = when (items[position].type) {
            ViewType.COURSE.type ->
                TYPE_RAIL_PORTRAIT
            ViewType.SERIES.type ->
                TYPE_RAIL_LANDSCAPE
            ViewType.CATEGORY.type ->
                TYPE_GRID_CATEGORY
            else -> TYPE_GRID_CATEGORY
        }
        vh.onCreated(layoutManagerType)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        setLayoutType(holder, position)
        holder.onBound(items, position)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetachedFromWindow()
    }

    class VH(
        private val binding: ItemParentDataBinding,
        private val scrollStateHolder: ScrollStateHolder
    ) :
        RecyclerView.ViewHolder(binding.root), ScrollStateHolder.ScrollStateKeyProvider {
        private lateinit var layoutManager: RecyclerView.LayoutManager
        private fun getLayoutManagerByType(layoutManagerType: Int): RecyclerView.LayoutManager =
            when (layoutManagerType) {
                TYPE_GRID_CATEGORY -> GridLayoutManager(
                    binding.root.context, 2,
                    RecyclerView.VERTICAL, false
                )
                else -> LinearLayoutManager(
                    binding.root.context,
                    RecyclerView.HORIZONTAL, false
                )
            }

        private val adapter = ChildAdapter()
        private val snapHelper = GravitySnapHelper(Gravity.START)
        private var currentItem: TitledList? = null

        override fun getScrollStateKey(): String? = currentItem?.title

        fun onCreated(layoutType: Int) {
            layoutManager = getLayoutManagerByType(layoutType)
            binding.nestedRecyclerView.adapter = adapter
            binding.nestedRecyclerView.layoutManager = layoutManager
            binding.nestedRecyclerView.setHasFixedSize(true)
            binding.nestedRecyclerView.itemAnimator?.changeDuration = 0
            snapHelper.attachToRecyclerView(binding.nestedRecyclerView)
            scrollStateHolder.setupRecyclerView(binding.nestedRecyclerView, this)
        }

        @Suppress("UNCHECKED_CAST")
        fun onBound(items: List<TitledList>, position: Int) {
            currentItem = items[position]
            //binding.divider.isVisible = position != items.size - 1
            binding.tvMediaCount.text = currentItem?.mediaCount.plus(" Episodes")
            binding.nestedTitleTextView.text = currentItem?.title
            val icon: String? = currentItem?.icon
            loadImageCircular(binding.ivChannelIcon, icon.toString())
            // conditional check on the item type
            when {
                currentItem?.list?.isMutableListOfType<LatestMedia>() == true -> {
                    adapter.setItems(currentItem?.list as List<LatestMedia>, currentItem?.type.toString())
                    Log.d(TAG, "onBound() called with: NewEpisodeItem = $currentItem")
                }
                currentItem?.list?.isMutableListOfType<Sery>() == true -> {
                    adapter.setItems(
                        currentItem?.list as List<Sery>,
                        currentItem?.type.toString()
                    )
                    Log.d(TAG, "onBound() called with: ChannelItem = $currentItem")
                } else -> {
                    adapter.setItems(
                        currentItem?.list as List<String>,
                        currentItem?.type.toString()
                    )
                    Log.d(TAG, "onBound() called with: StringType = $currentItem")
                }
            }
            scrollStateHolder.restoreScrollState(binding.nestedRecyclerView, this)
        }

        fun onRecycled() {
            scrollStateHolder.saveScrollState(binding.nestedRecyclerView, this)
            currentItem = null
        }

        /**
         * If we fast scroll while this ViewHolder's RecyclerView is still settling the scroll,
         * the view will be detached and won't be snapped correctly
         *
         * To fix that, we snap again without smooth scrolling.
         */
        fun onDetachedFromWindow() {
            if (binding.nestedRecyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                snapHelper.findSnapView(layoutManager)?.let {
                    val snapDistance = snapHelper.calculateDistanceToFinalSnap(layoutManager, it)
                    if (snapDistance[0] != 0 || snapDistance[1] != 0) {
                        binding.nestedRecyclerView.scrollBy(snapDistance[0], snapDistance[1])
                    }
                }
            }
        }
    }
}
