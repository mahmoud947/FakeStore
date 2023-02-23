package com.example.fakestore.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.repository.ProductRepository
import com.example.fakestore.ui.uiModel.HomeModel
import com.example.fakestore.utils.errorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {

    private val _categoryMap = MutableLiveData<DataState<Map<String, HomeModel>>>()
    val categoryMap: LiveData<DataState<Map<String, HomeModel>>> get() = _categoryMap

    private val _pagingProducts = MutableLiveData<PagingData<Product>>()
    val pagingProducts: LiveData<PagingData<Product>> get() = _pagingProducts
    private val map = mutableMapOf<String, HomeModel>()

    private val homeCategoryList =
        listOf("mens-shirts", "laptops", "womens-jewellery", "womens-dresses")

    init {
        getProductOFCategory()
        getPagingProducts()
    }


    private fun getProductOFCategory() {
        _categoryMap.postValue(DataState.Loading)
        viewModelScope.launch(Dispatchers.IO + errorHandler(_categoryMap)) {

            for (category in homeCategoryList) {
                val randomImage = repository.getRandomModelImage(category).urls.regular
                val products = repository.getProductsInCategory(category).map {
                    val favoriteProduct = async { repository.getFavoriteProduct(it.id) }
                    val product = it.copy(isFavorite = favoriteProduct.await() == it)
                    product
                }

                Log.e(TAG, "nnn: ${products.size}")
                // val homeModel: HomeModel = HomeModel(products = products, url = products[(0 ..homeCategoryList.size).random()].thumbnail)
                val homeModel: HomeModel = HomeModel(products = products, url = randomImage)
                map[category] = homeModel

            }
            _categoryMap.postValue(DataState.Success(map))
        }
    }

    fun updateFavoriteProduct(product: Product) {
        if (product.isFavorite) {
            map[product.category]?.products?.get(map[product.category]?.products!!.indexOf(product))?.isFavorite =
                !product.isFavorite
            removeProductFormFavorite(product.id)
            _categoryMap.postValue(DataState.Success(map))
        } else {
            map[product.category]?.products?.get(map[product.category]?.products!!.indexOf(product))?.isFavorite =
                !product.isFavorite
            _categoryMap.postValue(DataState.Success(map))
        }

    }

    private fun getPagingProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPagingProducts().asFlow().cachedIn(viewModelScope)
                .collect {
                    _pagingProducts.postValue(it)
                }
        }
    }

    fun addProductTOFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO + errorHandler()) {
            repository.addProductToFavorite(product)
        }
    }

    private fun removeProductFormFavorite(id:Int) {
        viewModelScope.launch(Dispatchers.IO + errorHandler()) {
            repository.removeProductFromFavorite(id)
        }
    }


}