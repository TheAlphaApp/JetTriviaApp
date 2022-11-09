package com.appdexon.jettriviaapp.data

import com.appdexon.jettriviaapp.model.TriviaQuestionsList

sealed class ApiResponse {
    //Loading Indicator
    object Loading: ApiResponse()

    //Error
    class Error(private val exception: Exception) : ApiResponse() {
        fun getMessage(): String? = exception.localizedMessage
    }

    //Data
    class Questions(val list: TriviaQuestionsList): ApiResponse() {
        fun getSize(): Int = list.size
    }
}
