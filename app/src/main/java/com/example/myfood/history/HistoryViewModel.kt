package com.example.myfood.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.Food
import com.example.myfood.myFoodList
import com.example.myfood.room.FoodRepository
import com.example.myfood.room.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state

    init {
        getHistory()
    }

    private fun getHistory() = viewModelScope.launch {
        repository.getAllHistory().onEach {
            _state.value = HistoryState(historyList = it)
        }.launchIn(viewModelScope)
    }

    private fun addHistory(foodId: Int) = viewModelScope.launch {
        val historyEntity = HistoryEntity(0L, foodId)
        repository.insertHistory(historyEntity)
    }

    private fun filterFoodIdsFromHistory(foodIds: List<Int>): List<Food> {
        val historyList = state.value.historyList
        return myFoodList.filter { food ->
            historyList.any { it.foodId == food.foodId && foodIds.contains(it.foodId) }
        }
    }

    fun getFilteredFoods(foodIds: List<Int>): List<Food> {
        return filterFoodIdsFromHistory(foodIds)
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.AddHistory -> {
                addHistory(event.foodId)
            }
        }
    }

}