package com.sam43.mindvalleychannels.ui

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.robolectric.annotation.Config

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class MainActivityTest: AppCompatActivity()