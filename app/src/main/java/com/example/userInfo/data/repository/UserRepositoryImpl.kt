package com.example.userInfo.data.repository

import com.example.userInfo.data.api.UserInfoService
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.toDomainModel
import com.example.userInfo.data.model.AddUserRequest
import com.example.userInfo.data.model.UserData
import com.example.userInfo.data.model.mapToEntity
import com.example.userInfo.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val userInfoService: UserInfoService
): UserInfoRepository {

    override suspend fun getUsers(): List<UserData> {
        val response = userInfoService.getUsers()
        val dbUsers = dao.getAllUsers()
        if (dbUsers.isNotEmpty()) {
            response.forEach { userData ->
                dbUsers.forEach { dbUser ->
                    val addedAtTime = if (dbUser.id == userData.id) {
                        dbUser.addedAt ?: System.currentTimeMillis() // don't add time
                    } else {
                        System.currentTimeMillis() // Use current time
                    }
                    dao.insertUser(userData.mapToEntity(addedAtTime))
                }
            }
        } else {
            dao.insertUsers(response.map { user -> user.mapToEntity() })
        }
        return dao.getAllUsers().map { it.toDomainModel() }
    }

    override suspend fun refreshUsers(): List<UserData> {
        return dao.getAllUsers().map { it.toDomainModel() }
    }

    override suspend fun addUser(user: UserData) {
        val response = userInfoService.addUser(AddUserRequest(user.name, user.email, "male", "active"))
        val statusCode = response.code()
        if (response.isSuccessful && (statusCode == 200 || statusCode == 201)) {
            dao.insertUser(user.mapToEntity())
        }
    }

    override suspend fun deleteUser(user: UserData) {
        TODO("Not yet implemented")
    }
}