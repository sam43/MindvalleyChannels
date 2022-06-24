package com.sam43.mindvalleychannels.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam43.mindvalleychannels.databinding.MainFragmentBinding
import com.sam43.mindvalleychannels.ui.adapters.ParentAdapter
import com.sam43.mindvalleychannels.ui.adapters.ScrollStateHolder
import com.sam43.mindvalleychannels.ui.adapters.viewholder.ViewType
import com.sam43.mindvalleychannels.ui.model.TitledList
import com.sam43.mindvalleychannels.utils.AppConstants
import com.sam43.mindvalleychannels.utils.AppConstants.TAG
import com.sam43.mindvalleychannels.utils.parser.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


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
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        adapter = ParentAdapter(scrollStateHolder)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadItems()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    private fun loadItems() {
        val lists = arrayListOf<TitledList>()
        repeat(3) { listIndex ->
            val items = mutableListOf<String>()
            repeat(10) { itemIndex -> items.add(itemIndex.toString()) }
            lists.add(TitledList("List number $listIndex", ViewType.values()[listIndex].type,
                items.toMutableList()
            ))
        }
        adapter.setItems(lists)
    }

}