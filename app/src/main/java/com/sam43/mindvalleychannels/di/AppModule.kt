package com.sam43.mindvalleychannels.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.data.local.AppDB
import com.sam43.mindvalleychannels.data.local.typeconverter.Converters
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.repository.MainRepositoryImpl
import com.sam43.mindvalleychannels.utils.AppConstants
import com.sam43.mindvalleychannels.utils.parser.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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



    @Singleton
    @Provides
    fun provideCurrencyApi(okHttpClient: OkHttpClient): Api = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(Api::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        okHttpProfilerInterceptor: OkHttpProfilerInterceptor
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(30L, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30L, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30L, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addInterceptor(okHttpProfilerInterceptor)
        }
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpProfilerInterceptor(): OkHttpProfilerInterceptor = OkHttpProfilerInterceptor()
}