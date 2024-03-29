package com.example.fakestore.data.remote

import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.models.response.LoginResponse
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.models.response.ProductsResponse
import com.example.fakestore.data.models.response.User
import retrofit2.http.*

interface FakeStoreApi {

    @POST("auth/login")
    suspend fun login(
        @Body credentials: Credentials
    ): LoginResponse

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

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int
    ): ProductsResponse

    @GET("users/{id}")
    suspend fun getUserInfo(
        @Path("id") id: Int
    ):User


}