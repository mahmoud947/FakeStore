package com.example.fakestore.utils

import androidx.lifecycle.MutableLiveData
import com.example.fakestore.core.data.DataState
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import retrofit2.Response

fun <T> errorHandler(
    dataState: MutableLiveData<DataState<T>>
): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
    dataState.postValue(DataState.Error(throwable))
}

fun errorHandler(
): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
}

fun <T> loginErrorHandler(
    dataState: MutableLiveData<DataState<T>>
): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
    when (throwable){
        is HttpException->{
            when(throwable.code()){
                400->dataState.postValue(DataState.MessageError(throwable.localizedMessage?:"Un known error"))
            }
        }
    }

}