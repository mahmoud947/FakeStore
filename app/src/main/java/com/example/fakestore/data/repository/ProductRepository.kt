package com.example.fakestore.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.local.ProductDao
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.models.response.LoginResponse
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.models.response.RandomPhotoResponseItem
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.UnsplashApi
import com.example.fakestore.data.repository.paginator.ProductPagingSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: FakeStoreApi,
    private val unsplashApi: UnsplashApi,
    private val dao: ProductDao
) : BaseRepository() {

    suspend fun getProducts(): List<Product> =
        api.getProducts().products

    fun getPagingProducts(): LiveData<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ProductPagingSource(api)
            }
        ).liveData

    suspend fun getProduct(id: Int): Product = api.getProduct(id)
    suspend fun getProductsInCategory(category: String): List<Product> =
        api.getProductsInCategory(category).products

    suspend fun getProducts(query: String): List<Product> = api.productSearch(query).products

    suspend fun getCategories(): List<String> = api.getCategories()

    suspend fun getRandomModelImage(query: String): RandomPhotoResponseItem =
        unsplashApi.getRandomPhoto(query = query).first()

    suspend fun login(credentials: Credentials): LoginResponse = api.login(credentials)

    fun getFavoriteProducts(): LiveData<List<Product>> = dao.getFavoriteProducts()

    suspend fun addProductToFavorite(product: Product) = dao.addToFavorite(product)
}