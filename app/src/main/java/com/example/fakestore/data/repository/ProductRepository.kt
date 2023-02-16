package com.example.fakestore.data.repository

import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.models.Product
import com.example.fakestore.data.remote.FakeStoreApi
import javax.inject.Inject

class ProductRepository @Inject constructor(
   private val api: FakeStoreApi
) :BaseRepository(){

    suspend fun getProducts():List<Product> =
        api.getProducts()

}