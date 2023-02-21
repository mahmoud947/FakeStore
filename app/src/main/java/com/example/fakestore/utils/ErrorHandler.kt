package com.example.fakestore.utils

import androidx.lifecycle.MutableLiveData
import com.example.fakestore.core.data.DataState
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

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
    when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                400 -> dataState.postValue(DataState.MessageError("User name or password is incorrect"))
                else->dataState.postValue(DataState.MessageError("unknown error"))
            }
        }
        is IOException->{
            dataState.postValue(DataState.MessageError("please check your internet connection and try again"))
        }
    }

}