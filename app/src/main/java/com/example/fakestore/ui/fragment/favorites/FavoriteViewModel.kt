package com.example.fakestore.ui.fragment.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {


    val favoriteProducts: LiveData<List<Product>> = repository.getFavoriteProducts()

    suspend fun addProductToFavorite(product: Product) = repository.addProductToFavorite(product)

}