package com.example.myfood.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfood.Food
import com.example.myfood.filter.FoodCard
import com.example.myfood.R
import com.example.myfood.Screen
import com.example.myfood.auth.AuthEvent
import com.example.myfood.auth.AuthViewModel
import com.example.myfood.history.HistoryEvent
import com.example.myfood.history.HistoryViewModel
import com.example.myfood.myFoodList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel()
) {

    var searchQuery by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var filteredFoods by remember { mutableStateOf(myFoodList) }

    Surface(modifier = Modifier.fillMaxSize()) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text(
                        text = "Welcome Again!",
                        modifier = Modifier.padding(25.dp),
                        fontWeight = Bold,
                        fontSize = 30.sp
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Home",
                                fontWeight = Bold,
                                fontSize = 24.sp
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Favourite", fontWeight = Bold, fontSize = 24.sp) },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.FavouriteScreen.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "History", fontWeight = Bold, fontSize = 24.sp) },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.HistoryScreen.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "About", fontWeight = Bold, fontSize = 24.sp) },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.AboutScreen.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Log Out", fontWeight = Bold, fontSize = 24.sp) },
                        selected = false,
                        onClick = {
                            authViewModel.onEvent(AuthEvent.LogoutEvent(""))
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    )
                }
            }) {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    MediumTopAppBar(
                        title = {
                            Text("Welcome Chef!")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.Menu, null)
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Filled.Notifications, null)
                            }
                        }
                    )
                },

                ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "What Would You Like to Cook Today?",
                        fontSize = 28.sp,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                filteredFoods = searchFood(searchQuery)
                            }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        OutlinedIconButton(
                            onClick = {
                                navController.navigate("filter_screen")
                            },
                            shape = RoundedCornerShape(50)
                            //modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
                        ) {
                            Icon(painter = painterResource(id = R.drawable.filter), null)
                        }
                    }

                    if (searchQuery.isBlank()) {
                        Spacer(modifier = Modifier.padding(top = 15.dp))
                        Text(
                            text = "As You Type, the Results Will Appear Here",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 20.sp,
                            fontWeight = Light
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            contentPadding = PaddingValues(bottom = 150.dp),
                            content = {
                                items(filteredFoods) { food ->
                                    FoodCard(
                                        food = food,
                                        onRecipeItemClick = {
                                            historyViewModel.onEvent(HistoryEvent.AddHistory(food.foodId))
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

fun searchFood(query: String): List<Food> {
    val lowerCaseQuery = query.toLowerCase()
    return myFoodList.filter { food ->
        food.foodTitle.toLowerCase().contains(lowerCaseQuery) ||
                food.foodDescription.toLowerCase().contains(lowerCaseQuery)
    }
}