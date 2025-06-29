package com.example.userInfo.domain.repository

import com.example.userInfo.data.model.UserData

interface UserInfoRepository {
    suspend fun getUsers(): List<UserData>
    suspend fun refreshUsers(): List<UserData>
    suspend fun addUser(user: UserData)
    suspend fun deleteUser(user: UserData)
}