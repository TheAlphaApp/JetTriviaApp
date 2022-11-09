package com.appdexon.jettriviaapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdexon.jettriviaapp.data.ApiResponse
import com.appdexon.jettriviaapp.model.CategoryOfQuiz
import com.appdexon.jettriviaapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {
    val responseMutableState: MutableState<ApiResponse> = mutableStateOf(ApiResponse.Loading)
    private val categoryOfQuizState: MutableState<CategoryOfQuiz?> = mutableStateOf(null)
    val questionIndex: MutableState<Int> = mutableStateOf(0)


    fun nextQuestion() {
        questionIndex.value += 1
    }

    private fun getResponseValue() {
        viewModelScope.launch {
            responseMutableState.value = repository.getApiResponse(categoryOfQuizState.value?.nameString)
        }
    }

    fun updateCategory(categoryOfQuiz: CategoryOfQuiz?) {
        categoryOfQuizState.value = categoryOfQuiz
        getResponseValue()
    }


    fun getCategoryTitle(): String {
        if (categoryOfQuizState.value != null) {
            return categoryOfQuizState.value!!.nameString.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        }
        return "Title"
    }
}