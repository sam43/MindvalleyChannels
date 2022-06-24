package com.sam43.mindvalleychannels.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam43.mindvalleychannels.data.remote.ErrorEventHandler.whenFailed
import com.sam43.mindvalleychannels.data.remote.ErrorEventHandler.whenFailedConnection
import com.sam43.mindvalleychannels.data.remote.ErrorEventHandler.whenLoading
import com.sam43.mindvalleychannels.data.remote.ResponseEvent
import com.sam43.mindvalleychannels.databinding.MainFragmentBinding
import com.sam43.mindvalleychannels.ui.adapters.ParentAdapter
import com.sam43.mindvalleychannels.ui.adapters.ScrollStateHolder
import com.sam43.mindvalleychannels.ui.adapters.viewholder.ViewType
import com.sam43.mindvalleychannels.ui.model.TitledList
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: ParentAdapter
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
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        adapter = ParentAdapter(scrollStateHolder)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadItems()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.channels.collectLatest { event ->
                when (event) {
                    is ResponseEvent.SuccessResponse<*> -> {
                        Log.d(TAG, "initObservers() called with: event = ${event.response.toString()}")
                    }
                    is ResponseEvent.ConnectionFailure -> whenFailedConnection(event)
                    is ResponseEvent.Failure -> whenFailed(event)
                    is ResponseEvent.Loading -> whenLoading(event)
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
                    is ResponseEvent.SuccessResponse<*> -> {
                        Log.d(TAG, "initObservers() called with: event = ${event.response.toString()}")
                    }
                    is ResponseEvent.ConnectionFailure -> whenFailedConnection(event)
                    is ResponseEvent.Failure -> whenFailed(event)
                    is ResponseEvent.Loading -> whenLoading(event)
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
                    is ResponseEvent.SuccessResponse<*> -> {
                        Log.d(TAG, "initObservers() called with: event = ${event.response.toString()}")
                    }
                    is ResponseEvent.ConnectionFailure -> whenFailedConnection(event)
                    is ResponseEvent.Failure -> whenFailed(event)
                    is ResponseEvent.Loading -> whenLoading(event)
                    else -> Log.d(
                        TAG,
                        "onCreate() called with: event = $event"
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    private fun loadItems() {
        viewModel.consumeRemoteChannels()
        val lists = arrayListOf<TitledList>()
        repeat(3) { listIndex ->
            val items = mutableListOf<String>()
            repeat(10) { itemIndex -> items.add(itemIndex.toString()) }
            lists.add(TitledList("List number $listIndex", ViewType.values()[listIndex].type,
                items.toMutableList()
            ))
        }
        Log.d(TAG, "loadItems() called: $lists")
        adapter.setItems(lists)
    }
}