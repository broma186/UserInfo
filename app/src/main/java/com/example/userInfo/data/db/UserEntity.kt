package com.example.userInfo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.userInfo.DateParser
import com.example.userInfo.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val addedAt: Long
)

fun UserEntity.toDomainModel(): User {
    return User(
        id = id.toString(),
        name = name,
        email = email,
        createdOn = DateParser.relativeTimeString(System.currentTimeMillis())
    )
}
