package com.sam43.mindvalleychannels.di

import com.sam43.mindvalleychannels.data.repository.MainRepository
import com.sam43.mindvalleychannels.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BindingModule::class]
)
@Module
abstract class BindingModuleTest {

    @Singleton
    @Binds
    abstract fun provideCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider

    @Singleton
    @Binds
    abstract fun provideRepository(
        mainRepository: MainRepositoryImpl
    ): MainRepository
}