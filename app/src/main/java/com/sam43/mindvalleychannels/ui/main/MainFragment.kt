package com.sam43.mindvalleychannels.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.EventView
import com.sam43.mindvalleychannels.databinding.MainFragmentBinding
import com.sam43.mindvalleychannels.ui.adapters.ChildAdapter
import com.sam43.mindvalleychannels.ui.adapters.ParentAdapter
import com.sam43.mindvalleychannels.ui.adapters.ScrollStateHolder
import com.sam43.mindvalleychannels.ui.adapters.viewholder.ViewType
import com.sam43.mindvalleychannels.ui.model.TitledList
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import com.sam43.mindvalleychannels.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val snapHelper: GravitySnapHelper by lazy { GravitySnapHelper(Gravity.START) }
    private lateinit var binding: MainFragmentBinding
    private lateinit var parentAdapter: ParentAdapter
    private lateinit var episodeAdapter: ChildAdapter
    private lateinit var categoryAdapter: ChildAdapter
    private lateinit var scrollStateHolder: ScrollStateHolder
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews(savedInstanceState)
        loadItems()
    }

    private fun initViews(savedInstanceState: Bundle?) {
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        setupParentAdapter()
        setupSingleAdapter()
        binding.swipeRefresh.setOnRefreshListener {
            loadItems()
        }
    }

    private fun setupParentAdapter() {
        parentAdapter = ParentAdapter(scrollStateHolder)
        binding.rvChannels.adapter = parentAdapter
        binding.rvChannels.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSingleAdapter() {
        // New Episode Linear Adapter
        episodeAdapter = ChildAdapter()
        binding.contentEpisode.rvEpisodes.adapter = episodeAdapter
        binding.contentEpisode.rvEpisodes.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.HORIZONTAL, false
        )
        binding.contentEpisode.rvEpisodes.setHasFixedSize(true)
        binding.contentEpisode.rvEpisodes.itemAnimator?.changeDuration = 0
        snapHelper.attachToRecyclerView(binding.contentEpisode.rvEpisodes)

        // Category Grid Adapter
        categoryAdapter = ChildAdapter()
        binding.contentCategory.rvCategories.adapter = categoryAdapter
        binding.contentCategory.rvCategories.layoutManager = GridLayoutManager(
            binding.root.context, 2,
            RecyclerView.VERTICAL, false
        )
        binding.contentCategory.rvCategories.setHasFixedSize(true)
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.channels.collectLatest { event ->
                when (event) {
                    is EventView.Success<*> -> {
                        binding.rvChannels.isVisible = true
                        binding.channelShimmerLayout.isVisible = false
                        Log.d(TAG, "initObservers() called with: event = ${event.response.toString()}")
                        val responseList = event.response as List<ChannelsIncludingCourseAndSeries>
                        val lists = arrayListOf<TitledList>()
                        responseList.forEach {
                            lists.add(
                                TitledList(
                                    it.channel.title,
                                    getViewType(it),
                                    it.channel.mediaCount.toString(),
                                    it.channel.iconAssetUrl,
                                    it.channel.coverAssetUrl,
                                    getDefinedList(it)
                                )
                            )
                        }
                        parentAdapter.setItems(lists)
                    }
                    is EventView.Failure ->  binding.root.showError(event.errorText)
                    else -> Log.d(
                        TAG,
                        "onCreate() called with: event = $event"
                    )
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.newEpisodes.collectLatest { event ->
                when (event) {
                    is EventView.Success<*> -> {
                        binding.contentEpisode.rvEpisodes.isVisible = true
                        binding.contentEpisode.epiShimmerLayout.isVisible = false
                        val list = event.response as List<EpisodeEntity>
                        binding.contentEpisode.tvTitle.text = getString(R.string.label_episodes)
                        Log.d(TAG, "initObservers() called with: media list = $list")
                        episodeAdapter.setItems(list, ViewType.COURSE.type)
                    }
                    is EventView.Failure ->  binding.root.showError(event.errorText)
                    else -> Log.d(
                        TAG,
                        "onCreate() called with: event = $event"
                    )
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.categories.collectLatest { event ->
                when (event) {
                    is EventView.Success<*> -> {
                        Log.d(TAG, "initObservers() called with: event = ${event.response.toString()}")
                        val list = event.response as List<CategoryEntity>
                        binding.contentCategory.tvCategoryTitle.text = getString(R.string.label_categories)
                        categoryAdapter.setItems(list, ViewType.CATEGORY.type)
                    }
                    is EventView.Failure ->  binding.root.showError(event.errorText)
                    else -> Log.d(
                        TAG,
                        "onCreate() called with: event = $event"
                    )
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.status.collectLatest { isLoading ->
                binding.contentEpisode.rvEpisodes.isVisible = !isLoading
                binding.contentEpisode.epiShimmerLayout.isVisible = isLoading
                binding.rvChannels.isVisible = !isLoading
                binding.channelShimmerLayout.isVisible = isLoading
                binding.swipeRefresh.isRefreshing = isLoading
            }
        }
    }

    private fun getDefinedList(it: ChannelsIncludingCourseAndSeries): MutableList<Any> =
        when {
            it.series.isNullOrEmpty() -> it.courses.toMutableList()
            else -> it.series.toMutableList()
        }

    private fun getViewType(it: ChannelsIncludingCourseAndSeries): String =
        when {
            it.series.isNullOrEmpty() -> ViewType.COURSE.type
            else -> ViewType.SERIES.type
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    private fun loadItems() {
        viewModel.consumeRemoteChannels()
        viewModel.consumeRemoteNewEpisodes()
        viewModel.consumeRemoteCategories()
        viewModel.fetchLoadingStatus()
    }
}