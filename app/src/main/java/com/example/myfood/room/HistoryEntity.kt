package com.example.myfood.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val historyId: Long,
    val foodId: Int
)