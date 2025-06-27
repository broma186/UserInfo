package com.example.userInfo.domain.usecase

import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.repository.UserInfoRepository

class UserInfoUseCase(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(): List<User> {
        return userInfoRepository.getUsers()
    }
}