package com.example.fakestore.data.repository.paginator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.remote.FakeStoreApi

class ProductPagingSource(
    private val api: FakeStoreApi
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val currentLoadingPageKey = params.key ?: 0
            val response = api.getAllProducts(skip = currentLoadingPageKey * 10)
            val responseData = mutableListOf<Product>()
            val products = response?.products ?: emptyList()
            responseData.addAll(products)
            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1
            val endOfPaginationReached = products.isEmpty()
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (endOfPaginationReached) null else currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}