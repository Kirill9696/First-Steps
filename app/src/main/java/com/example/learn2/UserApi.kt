package com.example.learn2

import retrofit2.http.GET

interface UserApi {

    @GET("users/1")
    suspend fun getUser(): UserDto
}