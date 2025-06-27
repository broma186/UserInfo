package com.example.userInfo.data.model

import java.time.LocalDate

data class UserData(
    val name: String,
    val email: String,
    val createdOn: LocalDate
)
