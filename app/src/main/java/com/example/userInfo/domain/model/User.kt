package com.example.userInfo.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val createdOn: String = "null"
)

