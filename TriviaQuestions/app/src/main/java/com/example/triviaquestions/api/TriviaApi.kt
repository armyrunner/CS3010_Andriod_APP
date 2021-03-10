package com.example.triviaquestions.api

import retrofit2.Call
import retrofit2.http.GET

interface TriviaApi {

    @GET("api.php?amount=40&type=boolean")
    fun fetchContents(): Call<TriviaResponse>
}