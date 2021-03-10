package com.example.starwarscharacters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.starwarscharacters.API.StarWarsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "StarWarsFetcher"

class StarWarsFetcher {

    private val starWarsAPI: StarWarsAPI

    init{

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://swapiclone.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        starWarsAPI = retrofit.create(StarWarsAPI::class.java)

    }


    fun fetchPhotos(): MutableLiveData<List<StarWarsItems>> {
        val responseLiveData: MutableLiveData<List<StarWarsItems>> = MutableLiveData()
        val starWarsRequest: Call<StarWarsResponse> = starWarsAPI.fetchPhotos()

        starWarsRequest.enqueue(object : Callback<StarWarsResponse> {
            override fun onFailure(call : Call<StarWarsResponse>, t:Throwable){
                Log.e(TAG,"Failed to fetch",t)
            }

            override fun onResponse(call: Call<StarWarsResponse>, response: Response<StarWarsResponse>) {
                Log.d(TAG,"Response Recieved}")
                val statWarsReponse:StarWarsResponse? = response.body()
                var starWarsItems: List<StarWarsItems> = statWarsReponse?.results
                    ?:mutableListOf()
                starWarsItems = starWarsItems.filterNot {
                    it.height.isBlank()
                }
                responseLiveData.value = starWarsItems
            }
        })
        return responseLiveData
    }
}