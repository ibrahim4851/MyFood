package com.example.myfood.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val username: String,
    val password: String
)