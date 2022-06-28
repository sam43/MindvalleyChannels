package com.sam43.mindvalleychannels.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@UninstallModules(BindingModule::class)
@Module
abstract class BindingModuleTest {
}