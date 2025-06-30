package com.example.userInfo.domain.usecase

import com.example.userInfo.domain.repository.UserInfoRepository

class RemoveUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return userInfoRepository.removeUser(id)
    }
}