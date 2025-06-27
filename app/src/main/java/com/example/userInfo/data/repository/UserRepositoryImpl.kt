package com.example.userInfo.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.userInfo.data.model.UserData
import com.example.userInfo.data.toDomainModel
import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
): UserInfoRepository {
    override suspend fun getUsers(): List<User> {
        val userList: List<UserData> = apolloClient.query(GetUsersQuery()).execute()
        return userList.map {
            it.toDomainModel()
        }
    }
}