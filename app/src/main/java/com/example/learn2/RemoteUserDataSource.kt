package com.example.learn2

import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val api: UserApi
) {

    suspend fun getUser(): UserDto {
        return api.getUser()
    }
}