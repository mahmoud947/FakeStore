package com.example.fakestore.di

import android.content.Context
import androidx.room.Room
import com.example.fakestore.data.local.ProductDao
import com.example.fakestore.data.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDao(
        @ApplicationContext context: Context
    ): ProductDao =
        ProductDatabase.getInstance(context = context).productDao
}