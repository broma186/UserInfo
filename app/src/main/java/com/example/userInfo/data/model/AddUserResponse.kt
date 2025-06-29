package com.example.userInfo.data.model

data class AddUserResponse(
    val name: String,
    val email: String,
    val gender: String? = null,
    val status: String? = null
)