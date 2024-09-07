package com.thiru.recipeapp.common

sealed class ResultState<T>(
    val data:T? = null,
    val message:String? = null
) {

    class Success<T>(data: T?): ResultState<T>(data)
    class Error<T>(message: String?, data: T? = null): ResultState<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): ResultState<T>(null)
}