package com.example.fakestore.ui.fragment.productDetail

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

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {
    private val _product = MutableLiveData<DataState<Product>>()
    val product: LiveData<DataState<Product>> get() = _product

    fun getProduct(id: Int) {
        handleData(filterCriteria = {
            repository.getProduct(id)
        }, data = _product)
    }

    private fun <T> handleData(
        filterCriteria: suspend () -> T,
        data: MutableLiveData<DataState<T>>
    ) {
        data.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO + errorHandler(data)) {
            val result = filterCriteria.invoke()
            data.postValue(DataState.Success(result))
        }
    }
}