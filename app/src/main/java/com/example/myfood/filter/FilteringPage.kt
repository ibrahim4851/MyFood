package com.example.myfood.filter

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfood.Food
import com.example.myfood.Screen
import com.example.myfood.history.HistoryEvent
import com.example.myfood.history.HistoryViewModel
import com.example.myfood.myFoodList
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    var filterCourseSet by remember { mutableStateOf(mutableSetOf<String>()) }
    var filterMealSet by remember { mutableStateOf(mutableSetOf<String>()) }
    var servingSliderPosition by remember { mutableFloatStateOf(1f) }
    var timeSliderPosition by remember { mutableFloatStateOf(15f) }
    var filteredFoods by remember { mutableStateOf(myFoodList) }


    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (showBottomSheet) {
                    BottomSheetScaffold(
                        scaffoldState = sheetState,
                        topBar = {
                            MediumTopAppBar(title = { Text("Filter Foods") })
                        },
                        sheetPeekHeight = 150.dp,
                        sheetContent = {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Filter")
                                    Text(
                                        text = "Reset",
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                filteredFoods = myFoodList
                                            }
                                        },
                                        color = Color(0xFFF55A00)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Meal", fontWeight = FontWeight.Bold)
                                    Text(text = "")
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    SelectableButton(
                                        text = "Breakfast",
                                        onToggle = {
                                            filterMealSet.add("Breakfast")
                                            filteredFoods = filterFoodsByMeal(filterMealSet)
                                        }
                                    )
                                    SelectableButton(
                                        text = "Lunch",
                                        onToggle = {
                                            filterMealSet.add("Lunch")
                                            filteredFoods = filterFoodsByMeal(filterMealSet)
                                        }
                                    )
                                    SelectableButton(
                                        text = "Dinner",
                                        onToggle = {
                                            filterMealSet.add("Dinner")
                                            filteredFoods = filterFoodsByMeal(filterMealSet)
                                        }
                                    )
                                }
                                Divider()
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(100.dp),
                                    contentPadding = PaddingValues(
                                        top = 16.dp,
                                        bottom = 16.dp,
                                        start = 8.dp,
                                        end = 8.dp
                                    ),
                                    content = {
                                        items(courseSelections) { course ->
                                            SelectableButton(
                                                modifier = Modifier.padding(
                                                    bottom = 8.dp,
                                                    start = 4.dp,
                                                    end = 4.dp
                                                ),
                                                text = course,
                                                onToggle = {
                                                    filterCourseSet.add(course)
                                                    filteredFoods =
                                                        filterFoodsByMealCourse(filterCourseSet)
                                                    Log.i("filterSet", filterCourseSet.toString())
                                                }
                                            )
                                        }
                                    }
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Serving (min)")
                                    Text(
                                        text = "Set Manually",
                                        modifier = Modifier.clickable {
                                            // TODO: RESET FILTER
                                        },
                                        color = Color(0xFFF55A00)
                                    )
                                }
                                Slider(
                                    value = servingSliderPosition,
                                    onValueChange = {
                                        servingSliderPosition = it
                                        filteredFoods =
                                            filterServing(servingSliderPosition.roundToInt())
                                    },
                                    steps = 7,
                                    valueRange = 0f..7f
                                )
                                Text(
                                    text = servingSliderPosition.roundToInt()
                                        .toString() + " servings"
                                )
                                Spacer(Modifier.padding(6.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Preparation Time (max)")
                                    Text(
                                        text = "Set Manually",
                                        modifier = Modifier.clickable {
                                            // TODO: RESET FILTER
                                        },
                                        color = Color(0xFFF55A00)
                                    )
                                }
                                Slider(
                                    value = timeSliderPosition,
                                    onValueChange = {
                                        timeSliderPosition = it
                                        filteredFoods = filterTime(timeSliderPosition.roundToInt())
                                    },
                                    valueRange = 5f..65f
                                )
                                Text(text = (timeSliderPosition.roundToInt()).toString() + " minutes")
                                Spacer(Modifier.padding(6.dp))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            sheetState.bottomSheetState.partialExpand()
                                        }
                                    },
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text("Apply")
                                }
                            }
                        }
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {

                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(150.dp),
                                contentPadding = PaddingValues(bottom = 150.dp),
                                content = {
                                    items(filteredFoods) { food ->
                                        FoodCard(food = food, onRecipeItemClick = {
                                            viewModel.onEvent(HistoryEvent.AddHistory(food.foodId))
                                            navController.navigate(Screen.FoodDetail.route + "/" + food.foodId.toString())
                                        })
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun filterServing(serving: Int): List<Food> {
    return myFoodList.filter { food ->
        food.foodServings > serving
    }
}

fun filterTime(time: Int): List<Food> {
    return myFoodList.filter { food ->
        food.foodTime < time
    }
}

fun filterFoodsByMealCourse(selectedMealTypes: Set<String>): List<Food> {
    return if (selectedMealTypes.isEmpty()) {
        // If no meal types are selected, return the original list
        myFoodList
    } else {
        // Filter the list by selected meal types
        myFoodList.filter { food ->
            food.foodMeal in selectedMealTypes
        }
    }
}

fun filterFoodsByMeal(selectedMealCourses: Set<String>): List<Food> {
    return if (selectedMealCourses.isEmpty()) {
        // If no meal types are selected, return the original list
        myFoodList
    } else {
        // Filter the list by selected meal types
        myFoodList.filter { food ->
            food.foodCourse in selectedMealCourses
        }
    }
}

fun removeFromSet(keyWord: String, keySet: Set<String>) {
    keySet.toMutableSet().remove(keyWord)
}

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    selectedColor: Color = Color(0xFFF55A00).copy(alpha = 0.25f),
    unselectedColor: Color = Color.Transparent,
    onToggle: () -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    OutlinedButton(
        modifier = modifier,
        onClick = {
            if (!isSelected) {
                onToggle()
            }
            isSelected = !isSelected
        },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) selectedColor else unselectedColor,
            contentColor = Color(0xFFF55A00),
            disabledContainerColor = unselectedColor,
            disabledContentColor = Color.Gray
        ),
        border = BorderStroke(
            width = ButtonDefaults.outlinedButtonBorder.width,
            color = if (isSelected) Color(0xFFF55A00) else Color.Gray
        ),
        content = { Text(text = text) }
    )
}

val courseSelections = listOf(
    "Soup",
    "Appetizer",
    "Starter",
    "Main Dish",
    "Side",
    "Dessert"
)