package com.anesoft.anno1800influencecalculator.base

sealed class ViewState<out T> where T : Any? {

    object Loading : ViewState<Nothing>()

    data class Success<T> (val data: T) : ViewState<T>()

    data class Failure(val errorMessage: String, val exception: Exception) : ViewState<Exception>()

    infix fun <T> ViewState<T>.takeIfSuccess(onSuccess: ViewState.Success<T>.() -> Unit): ViewState<T> {
        return when (this) {
            is ViewState.Success -> {
                onSuccess(this)
                this
            }
            else -> {
                this
            }
        }
    }

    infix fun <T> ViewState<T>.takeIfError(onError: ViewState.Failure.() -> Unit): ViewState<T> {
        return when (this) {
            is ViewState.Failure -> {
                onError(this)
                this
            }
            else -> {
                this
            }
        }
    }

}