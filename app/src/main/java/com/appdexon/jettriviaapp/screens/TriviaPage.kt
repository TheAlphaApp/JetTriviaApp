package com.appdexon.jettriviaapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appdexon.jettriviaapp.component.Questions
import com.appdexon.jettriviaapp.model.CategoryOfQuiz

@Composable
fun TriviaPage(
    navController: NavHostController,
    categoryString: String?
) {
    val categoryOfQuiz: CategoryOfQuiz? = CategoryOfQuiz.getEnumFromString(categoryString)
    val viewModel = hiltViewModel<QuestionViewModel>()
    viewModel.updateCategory(categoryOfQuiz)
    Questions(viewModel, navController)
}
