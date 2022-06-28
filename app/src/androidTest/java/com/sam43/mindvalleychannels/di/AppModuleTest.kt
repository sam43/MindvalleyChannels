package com.sam43.mindvalleychannels.di

import com.sam43.mindvalleychannels.repository.FakeMainRepositoryImpl
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.UninstallModules
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
    abstract fun provideRepository(
        mainRepository: FakeMainRepositoryImpl
    ): MainRepository
}