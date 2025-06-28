package com.example.userInfo.data.repository

import com.example.userInfo.data.api.UserInfoService
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.UserEntity
import com.example.userInfo.data.db.toDomainModel

import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val userInfoService: UserInfoService
): UserInfoRepository {

    override suspend fun getUsers(): List<User> {
        val response = userInfoService.getUsers()
        dao.insertUsers(response.map {
            user ->
            UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                addedAt = System.currentTimeMillis()
            )
        })
        return dao.getAllUsers().map { it.toDomainModel() }
    }

    override suspend fun addUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}