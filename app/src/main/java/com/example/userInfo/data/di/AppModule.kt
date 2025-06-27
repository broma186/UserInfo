package com.example.userInfo.data.di

import com.example.userInfo.data.repository.UserInfoRepositoryImpl
import com.example.userInfo.domain.repository.UserInfoRepository
import com.example.userInfo.domain.usecase.UserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(): UserInfoRepository {
        return UserInfoRepositoryImpl(UserInfoApolloClient)
    }

    @Provides
    @Singleton
    fun provideUserInfoUseCase(userInfoRepository: UserInfoRepository): UserInfoUseCase {
        return UserInfoUseCase(userInfoRepository)
    }
}