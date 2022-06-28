package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam43.mindvalleychannels.data.remote.EventState
import com.sam43.mindvalleychannels.data.remote.EventView
import com.sam43.mindvalleychannels.di.DispatcherProvider
import com.sam43.mindvalleychannels.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _channels = MutableStateFlow<EventView>(EventView.Loading)
    val channels: StateFlow<EventView> = _channels
    private val _newEpisodes = MutableStateFlow<EventView>(EventView.Loading)
    val newEpisodes: StateFlow<EventView> = _newEpisodes
    private val _categories = MutableStateFlow<EventView>(EventView.Loading)
    val categories: StateFlow<EventView> = _categories

    private val _status = MutableStateFlow<Boolean>(false)
    val status: StateFlow<Boolean> = _status

    fun fetchLoadingStatus() = viewModelScope.launch(dispatcher.io()) {
        combine(
            channels, newEpisodes, categories
        ) { ch, ne, ca ->
            ch is EventView.Loading || ne is EventView.Loading || ca is EventView.Loading
        }.collectLatest {
            _status.value = it
        }
    }

    fun consumeRemoteChannels() {
        _channels.value = EventView.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getChannelsData()
                .onEach { data ->
                    when (data) {
                        is EventState.Loading -> _channels.value =
                            EventView.Loading
                        is EventState.Failure -> _channels.value =
                            EventView.Failure(data.errorText)
                        is EventState.Success -> _channels.value =
                            EventView.Success(data.response)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteNewEpisodes() {
        _newEpisodes.value = EventView.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getNewEpisodesData()
                .onEach { data ->
                    when (data) {
                        is EventState.Loading -> _newEpisodes.value =
                            EventView.Loading
                        is EventState.Failure -> _newEpisodes.value =
                            EventView.Failure(data.errorText)
                        is EventState.Success -> _newEpisodes.value =
                            EventView.Success(data.response)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteCategories() {
        _categories.value = EventView.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getCategoriesData()
                .onEach { data ->
                    when (data) {
                        is EventState.Loading -> _categories.value =
                            EventView.Loading
                        is EventState.Failure -> _categories.value =
                            EventView.Failure(data.errorText)
                        is EventState.Success -> _categories.value =
                            EventView.Success(data.response)
                    }
                }.launchIn(this)
        }
    }
}