package com.example.userInfo.data.model

import com.example.userInfo.data.db.UserEntity

data class AddUserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String? = null,
    val status: String? = null
)

fun AddUserResponse.mapToEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        addedAt = System.currentTimeMillis()
    )
}