package com.example.fakestore.data.remote

import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.models.response.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeStoreApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @GET("/products/category/{category_name}")
    suspend fun getProductsInCategory(
        @Path("category_name")
        category: String
    ): ProductsResponse

    @GET("products/search")
    suspend fun productSearch(
        @Query("q")
        q: String
    ): ProductsResponse


    @GET("products/categories")
    suspend fun getCategories(): List<String>


    @GET("products/{product_id}")
    suspend fun getProduct(
        @Path("product_id")
        id: Int
    ): Product


}