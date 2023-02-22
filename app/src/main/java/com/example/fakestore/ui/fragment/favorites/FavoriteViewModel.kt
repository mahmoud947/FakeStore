package com.example.fakestore.ui.fragment.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {


    val favoriteProducts: LiveData<List<Product>> get() = repository.getFavoriteProducts()




}