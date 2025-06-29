package com.example.userInfo.data.api

import com.example.userInfo.data.model.AddUserRequest
import com.example.userInfo.data.model.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInfoService {

    @GET("public/v2/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserData>>

    @POST("public/v2/users")
    suspend fun addUser(@Body user: AddUserRequest): Response<UserData>
}