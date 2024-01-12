package com.example.myfood.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class, UserEntity::class], version = 1)
abstract class FoodDatabase: RoomDatabase() {
    abstract val dao: FoodDao
}