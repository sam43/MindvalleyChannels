package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam43.mindvalleychannels.data.remote.ResponseEvent
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

    private val _channels = MutableStateFlow<ResponseEvent>(ResponseEvent.Loading)
    val channels: StateFlow<ResponseEvent> = _channels

    fun consumeRemoteChannels() {
        _channels.value = ResponseEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
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

    fun consumeRemotePlaceholders() {
        _channels.value = ResponseEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
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
}