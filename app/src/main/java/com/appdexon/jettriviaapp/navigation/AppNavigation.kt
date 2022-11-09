package com.appdexon.jettriviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appdexon.jettriviaapp.screens.HomeScreen
import com.appdexon.jettriviaapp.screens.TriviaPage

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = AppScreens.HomeScreen.name
    ) {
        composable(AppScreens.HomeScreen.name) {

            //here we pass where this should lead us to
            HomeScreen(navController = navController)
        }

        //www.google.com/detail-screen/id=34
        composable(AppScreens.QuizScreen.name + "/{category}",
            arguments = listOf(navArgument(name = "category") {
                type = NavType.StringType
            })
        ) {  entry ->
            TriviaPage(
                navController = navController,
                categoryString = entry.arguments?.getString("category")
            )
        }
    }
}