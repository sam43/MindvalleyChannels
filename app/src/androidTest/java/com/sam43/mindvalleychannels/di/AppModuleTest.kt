package com.sam43.mindvalleychannels.di

import android.content.Context
import androidx.room.Room
import com.sam43.mindvalleychannels.data.local.AppDB
import com.sam43.mindvalleychannels.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@InstallIn(SingletonComponent::class)
@UninstallModules(AppModule::class)
@Module
object AppModuleTest {
    @Provides
    @Named(AppConstants.DATABASE_NAME)
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDB::class.java)
            .allowMainThreadQueries()
            .build()
}