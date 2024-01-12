package com.example.myfood

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfood.DbConstants.SHARED_PREF_USER_NAME
import com.example.myfood.about.AboutScreen
import com.example.myfood.auth.LoginScreen
import com.example.myfood.auth.SignUpScreen
import com.example.myfood.favourite.FavouriteScreen
import com.example.myfood.filter.FilterScreen
import com.example.myfood.fooddetail.FoodDetailScreen
import com.example.myfood.history.HistoryScreen
import com.example.myfood.welcome.WelcomeScreen

@Composable
fun ScreensNavigation() {

    val context = LocalContext.current
    val navController = rememberNavController()
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    val userId = sharedPreferences.getString(SHARED_PREF_USER_NAME, null)
    var startDestination = ""

    if (userId == null) {
        startDestination = Screen.LoginScreen.route
    } else {
        startDestination = Screen.HomeScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.HomeScreen.route) {
            WelcomeScreen(navController = navController)
        }

        composable(route = Screen.FilterScreen.route) {
            FilterScreen(navController = navController)
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screen.FoodDetail.route + "/{foodId}") { navBackStackEntry ->
            val foodId = navBackStackEntry.arguments?.getString("foodId")
            val food = myFoodList.find { it.foodId.toString() == foodId }
            FoodDetailScreen(navController = navController, food!!)
        }

        composable(route = Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }

        composable(route = Screen.AboutScreen.route) {
            AboutScreen(navController = navController)
        }

        composable(route = Screen.FavouriteScreen.route) {
            FavouriteScreen(navController = navController)
        }
    }
}


sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home_screen")
    data object FilterScreen : Screen(route = "filter_screen")
    data object HistoryScreen : Screen(route = "history_screen")
    data object AboutScreen : Screen(route = "about_screen")
    data object FoodDetail : Screen(route = "food_detail")
    data object FavouriteScreen : Screen(route = "favourite_screen")
    data object LoginScreen : Screen(route = "login_screen")
    data object SignUpScreen : Screen(route = "signup_screen")
}