package com.example.fakestore.ui.fragment.home

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {

    //TODO:DO IT WITH MAP
    private val _manCategory = MutableLiveData<DataState<List<Product>>>()
    val manCategory: LiveData<DataState<List<Product>>> get() = _manCategory

    private val _electronicsCategory = MutableLiveData<DataState<List<Product>>>()
    val electronicsCategory: LiveData<DataState<List<Product>>> get() = _electronicsCategory

    private val _jeweleryCategory = MutableLiveData<DataState<List<Product>>>()
    val jeweleryCategory: LiveData<DataState<List<Product>>> get() = _jeweleryCategory

    private val _womenCategory = MutableLiveData<DataState<List<Product>>>()
    val womenCategory: LiveData<DataState<List<Product>>> get() = _womenCategory

    init {
        getManProducts()
        getElectronicsProducts()
        getJeweleryProducts()
        getWomenProducts()
    }

    private fun getManProducts() {
        handleData(
            filterCriteria = {
                repository.getProductInCategory("mens-shirts")
            }, data = _manCategory
        )
    }

    private fun getElectronicsProducts() {
        handleData(
            filterCriteria = {
                repository.getProductInCategory("laptops")
            }, data = _electronicsCategory
        )
    }

    private fun getJeweleryProducts() {
        handleData(
            filterCriteria = {
                repository.getProductInCategory("womens-jewellery")
            }, data = _jeweleryCategory
        )
    }

    private fun getWomenProducts() {
        handleData(
            filterCriteria = {
                repository.getProductInCategory("womens-dresses")
            }, data = _womenCategory
        )
    }

    private fun <T> handleData(
        filterCriteria: suspend () -> T,
        data: MutableLiveData<DataState<T>>
    ) {
        data.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO + errorHandler(data)) {
            delay(1500)
            val result = filterCriteria.invoke()
            data.postValue(DataState.Success(result))
        }
    }
}