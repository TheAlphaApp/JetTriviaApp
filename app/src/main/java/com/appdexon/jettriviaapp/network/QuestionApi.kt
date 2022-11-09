package com.appdexon.jettriviaapp.network

import com.appdexon.jettriviaapp.model.TriviaQuestionsList
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("{category}.json")
    suspend fun getAllQuestions(@Path("category") category: String): TriviaQuestionsList
}