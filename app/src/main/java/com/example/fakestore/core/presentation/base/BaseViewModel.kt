package com.example.fakestore.core.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.DataState
import com.example.fakestore.utils.errorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

     fun <T> handleData(
        filterCriteria: suspend () -> T,
        data: MutableLiveData<DataState<T>>
    ) {
        data.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO + errorHandler(data)) {
            val result = filterCriteria.invoke()
            data.postValue(DataState.Success(result))
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}