package com.example.starwarscharacters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.starwarscharacters.API.StarWarsAPI

class StarWarsPHotosViewModel: ViewModel() {

    val starWarsItemsLiveData: LiveData<List<StarWarsItems>>

    init{

        starWarsItemsLiveData = StarWarsFetcher().fetchPhotos()

    }



}