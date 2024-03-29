package com.example.fakestore.ui.fragment.shop.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.repository.ProductRepository
import com.example.fakestore.utils.errorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CategoryViewModel"
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {
    private val _products = MutableLiveData<DataState<List<Product>>>()
    val products: LiveData<DataState<List<Product>>> get() = _products


    fun getProducts(category: String) {
        handleData(filterCriteria = {
            repository.getProductsInCategory(category)
        }, data = _products)

    }

    fun addProductTOFavorite(product: Product){
        viewModelScope.launch (Dispatchers.IO+ errorHandler()){
            repository.addProductToFavorite(product)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared:Category ViewModel Cleared")
    }

}