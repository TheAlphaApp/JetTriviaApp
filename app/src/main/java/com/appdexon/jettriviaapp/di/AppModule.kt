package com.appdexon.jettriviaapp.di

import com.appdexon.jettriviaapp.network.QuestionApi
import com.appdexon.jettriviaapp.repository.QuestionRepository
import com.appdexon.jettriviaapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi): QuestionRepository = QuestionRepository(api)

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(QuestionApi::class.java)
    }
}

