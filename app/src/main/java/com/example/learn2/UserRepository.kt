package com.example.learn2

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class User(
    val name: String,
    val profession: String,
)

class UserRepository @Inject constructor(
    private val api: UserApi
) {

    suspend fun getUser(): Resource<User> {
        return try {

            val userDto = api.getUser()

            Resource.Success(userDto.toUser())

        } catch (e: IOException) {

            Resource.Error(AppError.NoInternet)

        } catch (e: HttpException) {

            when (e.code()) {
                404 -> Resource.Error(AppError.NotFound)
                500 -> Resource.Error(AppError.Server)
                else -> Resource.Error(AppError.Unknown)
            }

        } catch (e: Exception) {

            Resource.Error(AppError.Unknown)
        }
    }
}