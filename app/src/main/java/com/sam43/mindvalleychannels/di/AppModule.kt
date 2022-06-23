package com.sam43.mindvalleychannels.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.sam43.mindvalleychannels.data.local.AppDB
import com.sam43.mindvalleychannels.data.local.typeconverter.Converters
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.repository.MainRepositoryImpl
import com.sam43.mindvalleychannels.utils.AppConstants
import com.sam43.mindvalleychannels.utils.parser.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // providers go from here
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDB {
        return Room.databaseBuilder(
            app,
            AppDB::class.java,
            AppConstants.DATABASE_NAME
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideChannelDao(appDB: AppDB) = appDB.channelsDao

    @Provides
    @Singleton
    fun provideCategoryDao(appDB: AppDB) = appDB.categoryDao

    @Provides
    @Singleton
    fun provideMediaDao(appDB: AppDB) = appDB.mediaDao

    @Provides
    @Singleton
    fun provideRepository() : MainRepository = MainRepositoryImpl()
}