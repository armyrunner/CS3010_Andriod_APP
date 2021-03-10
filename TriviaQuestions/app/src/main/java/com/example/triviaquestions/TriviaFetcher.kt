package com.example.triviaquestions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.triviaquestions.api.TriviaApi
import com.example.triviaquestions.api.TriviaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "TriviaFetcher"

class TriviaFetcher {

    private val triviaApi:TriviaApi

    init{

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        triviaApi = retrofit.create(TriviaApi::class.java)
    }

    fun fetchContents(): LiveData<List<TriviaItems>>{
        val responseLiveData: MutableLiveData<List<TriviaItems>> = MutableLiveData()
        val triviaRequest: Call<TriviaResponse> = triviaApi.fetchContents()

        triviaRequest.enqueue(object : Callback<TriviaResponse> {
            override fun onFailure(call : Call<TriviaResponse>, t:Throwable){
                Log.e(TAG,"Failed to fetch trivia questions",t)
            }

            override fun onResponse(call: Call<TriviaResponse>, response: Response<TriviaResponse>) {
                Log.d(TAG,"Response Recieved}")
                val triviaResponse:TriviaResponse? = response.body()
                var triviaItems: List<TriviaItems> = triviaResponse?.results
                    ?:mutableListOf()
                triviaItems = triviaItems.filterNot {
                    it.question.isBlank()
                }
                responseLiveData.value = triviaItems
            }
        })
        return responseLiveData
    }
}