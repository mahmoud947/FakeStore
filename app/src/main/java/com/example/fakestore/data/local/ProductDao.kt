package com.example.fakestore.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fakestore.data.models.response.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(product: Product)

    @Query("SELECT * FROM product")
    fun getFavoriteProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getFavoriteProduct(id: Int):Product

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteFromFavorite(id: Int)
}