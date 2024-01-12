package com.example.myfood.history

import com.example.myfood.room.HistoryEntity

data class HistoryState(
    val historyList: List<HistoryEntity> = listOf(),
    val historyEmptyMessage: String = ""
)
