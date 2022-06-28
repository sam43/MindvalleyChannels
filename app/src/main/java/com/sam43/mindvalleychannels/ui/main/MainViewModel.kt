package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam43.mindvalleychannels.data.remote.ResponseEvent
import com.sam43.mindvalleychannels.di.DispatcherProvider
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.utils.parser.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _channels = MutableStateFlow<ResponseEvent>(ResponseEvent.Loading)
    val channels: StateFlow<ResponseEvent> = _channels
    private val _newEpisodes = MutableStateFlow<ResponseEvent>(ResponseEvent.Loading)
    val newEpisodes: StateFlow<ResponseEvent> = _newEpisodes
    private val _categories = MutableStateFlow<ResponseEvent>(ResponseEvent.Loading)
    val categories: StateFlow<ResponseEvent> = _categories

    private val _status = MutableStateFlow<Boolean>(false)
    val status: StateFlow<Boolean> = _status

    fun fetchLoadingStatus() = viewModelScope.launch(dispatcher.io()) {
        combine(
            channels, newEpisodes, categories
        ) { ch, ne, ca ->
            ch is ResponseEvent.Loading || ne is ResponseEvent.Loading || ca is ResponseEvent.Loading
        }.collectLatest {
            _status.value = it
        }
    }

    fun consumeRemoteChannels() {
        _channels.value = ResponseEvent.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getChannelsData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _channels.value =
                            ResponseEvent.Loading
                        is Resource.NoInternet -> _channels.value =
                            ResponseEvent.ConnectionFailure(response.message!!)
                        is Resource.Error -> _channels.value =
                            ResponseEvent.Failure(response.message!!)
                        is Resource.Success -> _channels.value =
                            ResponseEvent.SuccessResponse(response.data)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteNewEpisodes() {
        _newEpisodes.value = ResponseEvent.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getMediaData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _newEpisodes.value =
                            ResponseEvent.Loading
                        is Resource.NoInternet -> _newEpisodes.value =
                            ResponseEvent.ConnectionFailure(response.message!!)
                        is Resource.Error -> _newEpisodes.value =
                            ResponseEvent.Failure(response.message!!)
                        is Resource.Success -> _newEpisodes.value =
                            ResponseEvent.SuccessResponse(response.data)
                    }
                }.launchIn(this)
        }
    }
    fun consumeRemoteCategories() {
        _categories.value = ResponseEvent.Loading
        viewModelScope.launch(dispatcher.io()) {
            repository.getCategoriesData()
                .onEach { response ->
                    when (response) {
                        is Resource.Loading -> _categories.value =
                            ResponseEvent.Loading
                        is Resource.NoInternet -> _categories.value =
                            ResponseEvent.ConnectionFailure(response.message!!)
                        is Resource.Error -> _categories.value =
                            ResponseEvent.Failure(response.message!!)
                        is Resource.Success -> _categories.value =
                            ResponseEvent.SuccessResponse(response.data)
                    }
                }.launchIn(this)
        }
    }
}