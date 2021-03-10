package com.example.starwarscharacters.API

import com.example.starwarscharacters.StarWarsResponse
import retrofit2.Call
import retrofit2.http.GET

interface StarWarsAPI{

    @GET("/people")
    fun fetchPhotos(): Call<StarWarsResponse>
}