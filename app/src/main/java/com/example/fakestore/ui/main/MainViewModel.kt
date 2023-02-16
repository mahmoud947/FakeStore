package com.example.fakestore.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.Product
import com.example.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {

    private val _products = MutableLiveData<DataState<List<Product>>>()
    val products: LiveData<DataState<List<Product>>> get() = _products

    init {
        getProducts()

    }

    private fun getProducts() {
        handleData(
            filterCriteria = {
                repository.getProducts()
            }, data = _products
        )
    }

}