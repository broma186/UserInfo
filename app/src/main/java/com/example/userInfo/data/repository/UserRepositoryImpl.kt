package com.example.userInfo.data.repository

import android.content.Context
import com.example.UserInfo.R
import com.example.userInfo.data.api.UserInfoService
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.toDomainModel
import com.example.userInfo.data.model.AddUserRequest
import com.example.userInfo.data.model.UserData
import com.example.userInfo.data.model.mapToEntity
import com.example.userInfo.domain.repository.UserInfoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val userInfoService: UserInfoService,
    @ApplicationContext private val context: Context
): UserInfoRepository {

    /*
    This function retrieves only the last page of users from the endpoint and manually added users.
    Initially it fetches the first 10 users just for header access.
    This header info is the total pages from the endpoint and is used as an index to find the last page.
     The users are then retrieved by id so fetched users and local db users can be merged so that the old added date isn't overwritten.
      The date shown for each user is based on the initial fetch time or manual add of the user.
     */
    override suspend fun getUsers(): List<UserData> {
        val response = userInfoService.getUsers(page = 1, perPage = 10) // For header page info
        val totalPages = response.headers()[context.getString(R.string.total_pages_header)]?.toIntOrNull() ?: 1
        val lastPageResponse = userInfoService.getUsers(page = totalPages, perPage = 10).body() // Last page

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

    override suspend fun addUser(name: String, email: String): Boolean {
        val response = userInfoService.addUser(AddUserRequest(name, email, context.getString(R.string.add_user_request_gender), context.getString(R.string.add_user_request_status)))
        val createdUser = response.body()
        val statusCode = response.code()
        return if (createdUser != null &&
            response.isSuccessful &&
            (statusCode == 200 || statusCode == 201)
        ) {
            dao.insertUser(createdUser.mapToEntity())
            true
        } else {
            false
        }
    }

    override suspend fun removeUser(id: Int): Boolean {
        val response = userInfoService.removeUser(id)
        return if (response.isSuccessful &&
            response.code() == 204
        ) {
            val userToDelete = dao.getUser(id)
            dao.removeUser(userToDelete)
            true
        } else {
            false
        }
    }
}