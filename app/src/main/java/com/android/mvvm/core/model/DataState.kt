package com.android.mvvm.core.model

sealed class DataState<out R>{
    data class Success<out T>(val data: T) : DataState<T>()
    data class Failure(val code: Int, val message: String) : DataState<Nothing>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}