package com.example.userInfo.data.di

import com.apollographql.apollo3.ApolloClient
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
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://gorest.co.in/graphql/")
            .addHttpHeader("Authorization", "2b25b1fa4fda3261d85ec984e02e4e3e847c3f562fec9a5d250c19e6c8e87f2c")
            .build()
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(apolloClient: ApolloClient): UserInfoRepository {
        return UserInfoRepositoryImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideUserInfoUseCase(userInfoRepository: UserInfoRepository): UserInfoUseCase {
        return UserInfoUseCase(userInfoRepository)
    }
}