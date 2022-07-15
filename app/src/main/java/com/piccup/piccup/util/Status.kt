package com.piccup.piccup.util

sealed class Status {
    data class Success<T>(
        var data: T?
    ) : Status()

    object SuccessLoadingMore : Status()

    data class Error(
        var code: Int = 0,
        var message: String? = ""
    ) : Status()

    data class ErrorLoadingMore(var code: Int = 0, var message: String?) : Status()

    object Loading : Status()

    object LoadingMore : Status()

    object Unauthorized : Status()


    data class OldVersion(var message: String?) : Status()

}