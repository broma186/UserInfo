package com.example.userInfo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.userInfo.data.model.UserData

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val addedAt: Long? = null
)

fun UserEntity.toDomainModel(): UserData {
    return UserData(
        id = id,
        name = name,
        email = email,
        addedAt = addedAt
    )
}
