package com.example.fakestore.data.repository

import androidx.lifecycle.LiveData
import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.local.ProductDao
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.models.response.LoginResponse
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.models.response.RandomPhotoResponseItem
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.UnsplashApi
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: FakeStoreApi,
    private val unsplashApi: UnsplashApi,
    private val dao: ProductDao
) : BaseRepository() {

    suspend fun getProducts(): List<Product> =
        api.getProducts().products

    suspend fun getProduct(id: Int): Product = api.getProduct(id)
    suspend fun getProductsInCategory(category: String): List<Product> =
        api.getProductsInCategory(category).products

    suspend fun getProducts(query: String): List<Product> = api.productSearch(query).products

    suspend fun getCategories(): List<String> = api.getCategories()

    suspend fun getRandomModelImage(query: String): RandomPhotoResponseItem =
        unsplashApi.getRandomPhoto(query = query).first()

    suspend fun login(credentials: Credentials): LoginResponse = api.login(credentials)

    fun getFavoriteProducts():LiveData<List<Product>> = dao.getFavoriteProducts()

    suspend fun addProductToFavorite(product: Product) = dao.addToFavorite(product)
}