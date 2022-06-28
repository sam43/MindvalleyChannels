package com.sam43.mindvalleychannels.di

import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModules {
    @Singleton
    @Binds
    abstract fun provideRepository(
        repository: MainRepositoryImpl
    ): MainRepository

    @Singleton
    @Binds
    abstract fun provideCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider
}