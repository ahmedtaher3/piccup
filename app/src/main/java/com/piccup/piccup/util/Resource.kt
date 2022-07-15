package com.piccup.piccup.util

sealed class Resource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(data: T , message: String) : Resource<T>(null, message)
    class Unauthorized<T>(data: T , message: String) : Resource<T>(null, message)
}