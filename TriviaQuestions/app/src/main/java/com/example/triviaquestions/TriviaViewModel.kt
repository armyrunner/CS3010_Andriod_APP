package com.example.triviaquestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TriviaViewModel : ViewModel() {

    val triviaItemsLiveData: LiveData<List<TriviaItems>>

    init{
        triviaItemsLiveData = TriviaFetcher().fetchContents()
    }

}