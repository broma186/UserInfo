package com.example.userInfo.data.di

import android.content.Context
import androidx.room.Room
import com.example.userInfo.Constants
import com.example.userInfo.data.api.UserInfoService
import com.example.userInfo.data.db.UserDao
import com.example.userInfo.data.db.UserDatabase
import com.example.userInfo.data.repository.UserInfoRepositoryImpl
import com.example.userInfo.domain.repository.UserInfoRepository
import com.example.userInfo.domain.usecase.AddUserInfoUseCase
import com.example.userInfo.domain.usecase.GetUserInfoUseCase
import com.example.userInfo.domain.usecase.RefreshUserInfoUseCase
import com.example.userInfo.domain.usecase.RemoveUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                provideAuthInterceptor()
            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor(Constants.AUTH_KEY)
    }

    @Provides
    @Singleton
    fun provideApiService(): UserInfoService {
        return Retrofit.Builder()
            .baseUrl("https://gorest.co.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(UserInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user_db").build()
    }

    @Provides
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(dao: UserDao, userInfoService: UserInfoService, @ApplicationContext context: Context): UserInfoRepository {
        return UserInfoRepositoryImpl(dao, userInfoService, context)
    }

    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(userInfoRepository: UserInfoRepository): GetUserInfoUseCase {
        return GetUserInfoUseCase(userInfoRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshUserInfoUseCase(userInfoRepository: UserInfoRepository): RefreshUserInfoUseCase {
        return RefreshUserInfoUseCase(userInfoRepository)
    }

    @Provides
    @Singleton
    fun provideAddUserInfoUseCase(userInfoRepository: UserInfoRepository): AddUserInfoUseCase {
        return AddUserInfoUseCase(userInfoRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveUserInfoUseCase(userInfoRepository: UserInfoRepository): RemoveUserInfoUseCase {
        return RemoveUserInfoUseCase(userInfoRepository)
    }
}