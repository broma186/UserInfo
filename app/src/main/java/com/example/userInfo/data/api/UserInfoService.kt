package com.example.userInfo.data.api

import com.example.userInfo.data.model.AddUserRequest
import com.example.userInfo.data.model.AddUserResponse
import com.example.userInfo.data.model.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoService {

    @GET("public/v2/users")
    suspend fun getUsers(): List<UserData>

    @POST("public/v2/users")
    suspend fun addUser(@Body user: AddUserRequest): Response<AddUserResponse>
}