package com.example.userInfo.data.model

import com.example.userInfo.DateParser
import com.example.userInfo.data.db.UserEntity
import com.example.userInfo.domain.model.User
import com.google.gson.annotations.Expose

data class UserData(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String? = null,
    val status: String? = null,
    @Expose(serialize = false, deserialize = false)
    val addedAt: Long? = null
)

fun UserData.mapToUI(): User {
    return User(
        id = id,
        name = name,
        email = email,
        createdOn = DateParser.relativeTimeString(addedAt ?: System.currentTimeMillis())
    )
}

fun UserData.mapToEntity(addedAt: Long = System.currentTimeMillis()): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        addedAt = addedAt
    )
}