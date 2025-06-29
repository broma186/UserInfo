package com.example.userInfo.domain.usecase

import com.example.userInfo.data.model.UserData
import com.example.userInfo.domain.repository.UserInfoRepository

class GetUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(): List<UserData> {
        return userInfoRepository.getUsers()
    }
}