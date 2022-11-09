package com.appdexon.jettriviaapp.repository

import android.util.Log
import com.appdexon.jettriviaapp.data.ApiResponse
import com.appdexon.jettriviaapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    suspend fun getApiResponse(categoryString: String?): ApiResponse {
        var apiResponse: ApiResponse = ApiResponse.Loading

        try {
            if (categoryString != null) {
                val listOfQuestions = ApiResponse.Questions(api.getAllQuestions(categoryString))
                if (listOfQuestions.list.isNotEmpty()) apiResponse = listOfQuestions
            }
        } catch (exception: Exception) {
            apiResponse = ApiResponse.Error(exception)
            val localizedMessage = apiResponse.getMessage()
            Log.d("EXCEPTION", "getAllQuestions: $localizedMessage")
        }
        return apiResponse
    }


}