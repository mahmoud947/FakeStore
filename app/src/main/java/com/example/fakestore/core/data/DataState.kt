package com.example.fakestore.core.data

sealed class DataState<out T>() {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val exception: Throwable? = null) : DataState<Nothing>()
    data class MessageError(val errorMessage: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    fun isSuccessful(): Boolean = this is Success
    fun isFailed(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading

    fun getMessage(): String{
        return if (this is MessageError) {
            this.errorMessage
        } else {
            "Un known Error"
        }
    }

    fun isEmpty(): Boolean {
        if (this is Success) {
            return if (this.data == null) {
                true
            } else {
                this.data is List<*> && this.data.isEmpty()
            }
        }

        return true
    }


}

fun <T> DataState<T>.getData(): T? {
    if (this is DataState.Success)
        return this.data
    return null
}