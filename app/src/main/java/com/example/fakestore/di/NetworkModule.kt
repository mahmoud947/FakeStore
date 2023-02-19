package com.example.fakestore.di

import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.UnsplashApi
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
import kotlin.math.log

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

    @Provides
    @Singleton
    fun provideFakeStoreApi(builder: Retrofit.Builder): FakeStoreApi =
        builder
            .baseUrl("https://dummyjson.com/")
            .build().create(FakeStoreApi::class.java)

    @Provides
    @Singleton
    fun provideUnsplashApi(builder: Retrofit.Builder): UnsplashApi =
        builder
            .baseUrl("https://api.unsplash.com/")
            .build().create(UnsplashApi::class.java)

}