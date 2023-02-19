package com.example.fakestore.di

import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.UnsplashApi
import com.example.fakestore.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        api: FakeStoreApi,
        unsplashApi: UnsplashApi
    ): ProductRepository = ProductRepository(api = api,unsplashApi)
}