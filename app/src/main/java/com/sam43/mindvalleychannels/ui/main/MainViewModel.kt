package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.*
import com.sam43.mindvalleychannels.data.local.entity.CategoryEntity
import com.sam43.mindvalleychannels.data.local.entity.ChannelsIncludingCourseAndSeries
import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.utils.parser.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class EventView {
        class Success<T>(val response: T) : EventView()
        class Error(val errorText: String) : EventView()
        object Loading : EventView()
    }


    init {
        //refreshData()
    }

//    private fun refreshData() {
//        _channels.value = EventView.Loading
//        _newEpisodes.value = EventView.Loading
//        _categories.value = EventView.Loading
//    }

    private val _channels = MutableStateFlow<EventView>(EventView.Loading)
    val channels: StateFlow<EventView> = _channels
    private val _newEpisodes = MutableStateFlow<EventView>(EventView.Loading)
    val newEpisodes: StateFlow<EventView> = _newEpisodes
    private val _categories = MutableStateFlow<EventView>(EventView.Loading)
    val categories: StateFlow<EventView> = _categories
    private val _status: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val status = _status

    fun fetchChannels() = viewModelScope.launch(Dispatchers.IO) {
        repository.getChannelsData().onEach {
            when(it) {
                is EventState.Failure -> _channels.value = EventView.Error(it.errorText)
                is EventState.Loading -> _channels.value = EventView.Loading
                is EventState.Success -> _channels.value = EventView.Success(it.response)
            }
        }.launchIn(this)
    }
    fun fetchNewEpisodes() = viewModelScope.launch(Dispatchers.IO) {
        repository.getNewEpisodesData().onEach {
            when(it) {
                is EventState.Failure -> _newEpisodes.value = EventView.Error(it.errorText)
                is EventState.Loading -> _newEpisodes.value = EventView.Loading
                is EventState.Success -> _newEpisodes.value = EventView.Success(it.response)
            }
        }.launchIn(this)
    }
    fun fetchCategories() = viewModelScope.launch(Dispatchers.IO) {
        repository.getCategoriesData().onEach {
            when(it) {
                is EventState.Failure -> _categories.value = EventView.Error(it.errorText)
                is EventState.Loading -> _categories.value = EventView.Loading
                is EventState.Success -> _categories.value = EventView.Success(it.response)
            }
        }.launchIn(this)
    }

    fun checkIfLoading() = viewModelScope.launch(Dispatchers.IO) {
        combine(newEpisodes, channels, categories) { ne, ch, cat ->
            ne is EventView.Loading || ch is EventView.Loading || cat is EventView.Loading
        }.collectLatest { isLoading ->
            _status.value = isLoading
        }
    }
}