package com.appdexon.jettriviaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appdexon.jettriviaapp.model.CategoryOfQuiz
import com.appdexon.jettriviaapp.navigation.AppScreens
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = {Text("Choose Category: ")})  },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp,0.dp,16.dp,16.dp)
        ) {
            items(
                items = CategoryOfQuiz.values()
            ) {
                    CategoryCard(categoryOfQuiz = it) { category ->
                        navController.navigate(AppScreens.QuizScreen.name + "/${category.nameString}")
                    }
            }
        }
    }
}




@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    categoryOfQuiz: CategoryOfQuiz,
    onItemClick: (categoryOfQuiz1: CategoryOfQuiz) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onItemClick(categoryOfQuiz)
            }
    ) {
        Text(text = categoryOfQuiz.nameString.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }, style = MaterialTheme.typography.h4, modifier = Modifier.padding(8.dp))
    }

}