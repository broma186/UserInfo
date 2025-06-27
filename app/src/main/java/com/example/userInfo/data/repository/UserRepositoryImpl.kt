package com.example.userInfo.data.repository

import com.example.userInfo.data.model.UserData
import com.example.userInfo.data.toDomainModel
import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl(private val userDataSource: UserInfoApolloClient): UserInfoRepository {
    override suspend fun getUsers(): List<User> {
        val userList: List<UserData> = userDataSource.getUsers()
        return userList.map {
            it.toDomainModel()
        }
    }
}