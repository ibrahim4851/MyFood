package com.example.myfood.history

sealed class HistoryEvent {
    data class AddHistory(val foodId: Int): HistoryEvent()
}
