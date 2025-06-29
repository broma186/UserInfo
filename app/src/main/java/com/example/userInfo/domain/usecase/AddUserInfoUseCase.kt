package com.example.userInfo.domain.usecase

import com.example.userInfo.data.model.UserData
import com.example.userInfo.domain.repository.UserInfoRepository

class AddUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(user: UserData) {
        return userInfoRepository.addUser(user)
    }
}