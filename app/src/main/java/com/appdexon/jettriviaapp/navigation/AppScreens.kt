package com.appdexon.jettriviaapp.navigation

import java.lang.IllegalArgumentException

enum class AppScreens {
    HomeScreen,
    QuizScreen;
    companion object {
        fun fromRoute(route: String?): AppScreens
                = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            QuizScreen.name -> QuizScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}