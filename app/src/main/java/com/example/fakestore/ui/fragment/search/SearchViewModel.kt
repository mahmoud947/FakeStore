package com.example.fakestore.ui.fragment.search

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
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {

    private val _searchResult = MutableLiveData<DataState<List<Product>>>()
    val searchResult: LiveData<DataState<List<Product>>> get() = _searchResult


    fun search(query: String) {
        handleData(filterCriteria = {
            repository.getProducts(query)
        }, _searchResult)
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