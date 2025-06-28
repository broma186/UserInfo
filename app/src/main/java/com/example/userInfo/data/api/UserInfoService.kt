package com.example.userInfo.data.api

import com.example.userInfo.data.model.UserData
import retrofit2.http.GET

interface UserInfoService {

    @GET("public/v2/users")
    suspend fun getUsers(): List<UserData>
}