package com.example.learn2

sealed class Resource<out T> {

    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error(
        val error: AppError
    ) : Resource<Nothing>()
}