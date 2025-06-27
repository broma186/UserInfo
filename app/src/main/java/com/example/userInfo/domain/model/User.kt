package com.example.userInfo.domain.model

import java.time.LocalDate

data class User(
    val name: String,
    val email: String,
    val createdOn: LocalDate
)