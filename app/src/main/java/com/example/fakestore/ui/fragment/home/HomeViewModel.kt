package com.example.fakestore.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
}