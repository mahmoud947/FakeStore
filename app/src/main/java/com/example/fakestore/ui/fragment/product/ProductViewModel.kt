package com.example.fakestore.ui.fragment.product

import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {
}