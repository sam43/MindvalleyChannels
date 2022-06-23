package com.sam43.mindvalleychannels.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sam43.mindvalleychannels.databinding.MainActivityBinding
import com.sam43.mindvalleychannels.ui.main.MainFragment
import com.sam43.mindvalleychannels.utils.AppConstants.transparentStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.transparentStatusBar()
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}