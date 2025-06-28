package com.example.userInfo.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.userInfo.GetUsersQuery
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.UserEntity
import com.example.userInfo.data.db.toDomainModel
import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val apolloClient: ApolloClient
): UserInfoRepository {

    override suspend fun getUsers(): List<User> {
        val response = apolloClient.query(GetUsersQuery()).execute()
        Log.d("TEST", "response: $response")
        dao.insertUsers(response.data?.users?.nodes?.filterNotNull()?.map {
            user ->
            UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                addedAt = System.currentTimeMillis()
            )
        } ?: emptyList())
        return dao.getAllUsers().map { it.toDomainModel() }
    }

    override suspend fun addUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}