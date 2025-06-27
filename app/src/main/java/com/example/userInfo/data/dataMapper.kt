package com.example.userInfo.data

import com.example.userInfo.data.model.UserData
import com.example.userInfo.domain.model.User

fun UserData.toDomainModel(): User {
    return User(
        name = name,
        email = email,
        createdOn = createdOn
    )
}