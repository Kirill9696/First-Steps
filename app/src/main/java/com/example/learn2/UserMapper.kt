package com.example.learn2

fun UserDto.toUser(): User {
    return User(
        name = name,
        profession = username
    )
}

fun UserDto.toUserName(): UserName {
    return UserName(
        name = name
    )
}