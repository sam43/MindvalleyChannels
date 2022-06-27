package com.sam43.mindvalleychannels.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //window.transparentStatusBar()
        binding = MainActivityBinding.inflate(layoutInflater)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_normal))
        setContentView(binding.root)
    }
}