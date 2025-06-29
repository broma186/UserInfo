package com.example.userInfo.data.model

data class AddUserRequest(
    val name: String,
    val email: String,
    val gender: String? = null,
    val status: String? = null
)