package com.example.userInfo.domain.repository

import com.example.userInfo.domain.model.User

interface UserInfoRepository {
    suspend fun getUsers(): List<User>
}