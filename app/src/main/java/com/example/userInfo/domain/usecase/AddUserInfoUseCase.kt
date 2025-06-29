package com.example.userInfo.domain.usecase

import com.example.userInfo.domain.repository.UserInfoRepository

class AddUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(name: String, email: String) {
        return userInfoRepository.addUser(name, email)
    }
}