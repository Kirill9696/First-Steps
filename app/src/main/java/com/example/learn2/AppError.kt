package com.example.learn2

sealed class AppError {
    data object NoInternet : AppError()
    data object NotFound : AppError()
    data object Server : AppError()
    data object Unknown : AppError()
}