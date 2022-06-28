package com.sam43.mindvalleychannels.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.sam43.mindvalleychannels.BuildConfig
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        okHttpProfilerInterceptor: OkHttpProfilerInterceptor,
        @Named("cache") cache: Interceptor
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val responseCache = Cache(context.cacheDir, cacheSize)
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(15L, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(15L, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(15L, TimeUnit.SECONDS)
        okHttpClientBuilder.cache(responseCache)
        okHttpClientBuilder.addInterceptor(cache)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addInterceptor(okHttpProfilerInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheMemory(@Named("hasNetwork") hasNetwork: Boolean): Interceptor =
        Interceptor { chain ->

            // Get the request from the chain.
            var request = chain.request()

            /*
            *  Leveraging the advantage of using Kotlin,
            *  we initialize the request and change its header depending on whether
            *  the device is connected to Internet or not.
            */
            request = if (hasNetwork)
            /*
            *  If there is Internet, get the cache that was stored 5 seconds ago.
            *  If the cache is older than 5 seconds, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
            /*
            *  If there is no Internet, get the cache that was stored 7 day ago.
            *  If the cache is older than 7 day, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-stale' attribute is responsible for this behavior.
            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
            */
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            // End of if-else statement

            // Add the modified request to the chain.
            chain.proceed(request)
        }

    @Provides
    @Singleton
    @Named("hasNetwork")
    fun provideHasNetwork(@ApplicationContext context: Context): Boolean {
        var isConnected = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
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


@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Singleton
    @Binds
    abstract fun provideCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider


    @Singleton
    @Binds
    abstract fun provideRepository(
        mindValleyRepository: MainRepositoryImpl
    ): MainRepository
}