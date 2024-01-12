package com.example.myfood.room

import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    fun getAllHistory(): Flow<List<HistoryEntity>>

    suspend fun insertHistory(historyEntity: HistoryEntity): Long

    suspend fun loginUser(username: String, password: String): UserEntity?

    suspend fun signUpUser(user: UserEntity)

    suspend fun getUserByUsername(username: String): UserEntity?

}