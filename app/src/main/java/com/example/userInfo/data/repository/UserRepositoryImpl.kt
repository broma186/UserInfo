package com.example.userInfo.data.repository

import com.example.userInfo.data.api.UserInfoService
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.UserEntity
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
        val response = userInfoService.getUsers(page = 1, perPage = 10)
        val totalPages = response.headers()["x-pagination-pages"]?.toIntOrNull() ?: 1
        val lastPageResponse = userInfoService.getUsers(page = totalPages, perPage = 10).body()

        val dbUsersById = dao.getAllUsers().associateBy { it.id }
        val mergedUsers = lastPageResponse?.map { userData ->
            val existing = dbUsersById[userData.id]
            val addedAt = existing?.addedAt ?: System.currentTimeMillis()
            userData.mapToEntity(addedAt)
        } ?: emptyList()

        dao.insertUsers(mergedUsers)
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
        // TODO: Add user removal.
       // dao.removeUser(user.mapToEntity())
    }
}