package com.imthiyas.moviediscoveryapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.imthiyas.moviediscoveryapp.data.request.Movie
import com.imthiyas.moviediscoveryapp.ui.HomeScreen
import com.imthiyas.moviediscoveryapp.ui.screens.DetailsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{movieJson}") {
        fun passMovie(movie: Movie): String {
            val movieJson = Gson().toJson(movie)
            return "details/$movieJson"
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Details.route) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("movieJson")
            val movie = Gson().fromJson(json, Movie::class.java)
            DetailsScreen(movie)
        }
    }
}
