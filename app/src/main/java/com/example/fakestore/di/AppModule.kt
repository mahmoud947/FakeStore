package com.example.fakestore.di

import android.content.Context
import android.content.SharedPreferences
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.UnsplashApi
import com.example.fakestore.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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

    @Singleton
    @Provides
    @Named("Auth")
    fun provideAuthPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)

}