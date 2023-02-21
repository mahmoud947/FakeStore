package com.example.fakestore.data.local

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.*
import com.example.fakestore.data.local.utils.DATABASE_NAME
import com.example.fakestore.data.models.response.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao


    // singleton pattern
    companion object {
        private lateinit var INSTANCE: ProductDatabase
        fun getInstance(context: Context): ProductDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}