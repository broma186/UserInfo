package com.example.userInfo.domain.model

data class User(
    val name: String,
    val email: String,
    val createdOn: String = "null"
)