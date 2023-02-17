package com.example.fakestore.data.remote

import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.models.response.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @GET("/products/category/{category_name}")
    suspend fun getProductInCategory(
        @Path("category_name")
        category: String
    ): ProductsResponse


}