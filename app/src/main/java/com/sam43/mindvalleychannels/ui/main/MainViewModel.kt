package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.utils.parser.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _channels = MutableStateFlow<EventState>(EventState.Loading)
    val channels: StateFlow<EventState> = _channels
    private val _newEpisodes = MutableStateFlow<EventState>(EventState.Loading)
    val newEpisodes: StateFlow<EventState> = _newEpisodes
    private val _categories = MutableStateFlow<EventState>(EventState.Loading)
    val categories: StateFlow<EventState> = _categories

    fun consumeRemoteChannels() {
        _channels.value = EventState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getChannelsData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _channels.value =
                            EventState.Loading
                        is Resource.NoInternet -> _channels.value =
                            EventState.ConnectionFailure(response.message!!)
                        is Resource.Error -> _channels.value =
                            EventState.Failure(response.message!!)
                        is Resource.Success -> _channels.value =
                            EventState.Success(response.data)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteNewEpisodes() {
        _newEpisodes.value = EventState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewEpisodesData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _newEpisodes.value =
                            EventState.Loading
                        is Resource.NoInternet -> _newEpisodes.value =
                            EventState.ConnectionFailure(response.message!!)
                        is Resource.Error -> _newEpisodes.value =
                            EventState.Failure(response.message!!)
                        is Resource.Success -> _newEpisodes.value =
                            EventState.Success(response.data)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteCategories() {
        _categories.value = EventState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoriesData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _categories.value =
                            EventState.Loading
                        is Resource.NoInternet -> _categories.value =
                            EventState.ConnectionFailure(response.message!!)
                        is Resource.Error -> _categories.value =
                            EventState.Failure(response.message!!)
                        is Resource.Success -> _categories.value =
                            EventState.Success(response.data)
                    }
                }.launchIn(this)
        }
    }
}