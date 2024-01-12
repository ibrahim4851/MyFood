package com.example.myfood.room

import kotlinx.coroutines.flow.Flow

class FoodRepositoryImpl(private val dao: FoodDao) : FoodRepository {
    override fun getAllHistory(): Flow<List<HistoryEntity>> {
        return dao.getAllHistory()
    }

    override suspend fun insertHistory(historyEntity: HistoryEntity): Long {
        return dao.insertHistory(historyEntity)
    }

    override suspend fun loginUser(username: String, password: String): UserEntity? {
        return dao.loginUser(username, password)
    }

    override suspend fun signUpUser(user: UserEntity) {
        return dao.signUpUser(user)
    }

    override suspend fun getUserByUsername(username: String): UserEntity? {
        return dao.getUserByUsername(username)
    }
}