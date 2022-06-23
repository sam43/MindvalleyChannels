package com.sam43.mindvalleychannels.ui.main

import androidx.lifecycle.ViewModel
import com.sam43.mindvalleychannels.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
    ) : ViewModel() {
}