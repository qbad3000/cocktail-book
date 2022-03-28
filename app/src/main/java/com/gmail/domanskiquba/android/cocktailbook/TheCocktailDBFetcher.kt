package com.gmail.domanskiquba.android.cocktailbook

import android.util.Log
import androidx.lifecycle.*
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBApi
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "TheCocktailDBFetcher"

class TheCocktailDBFetcher {

    private val theCocktailDBApi: TheCocktailDBApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        theCocktailDBApi = retrofit.create(TheCocktailDBApi::class.java)
    }

    fun fetchCocktailsList(): LiveData<List<Cocktail>> {
        val responseLiveData: MediatorLiveData<List<Cocktail>> = MediatorLiveData()

        for(letter in 'a'..'z') {
            val fetchCocktailsByLetterResponse = fetchCocktailsByLetter(letter)
            responseLiveData.addSource(fetchCocktailsByLetterResponse) {
                responseLiveData.value = (responseLiveData.value?.plus(it) ?: it)
                    .distinct().shuffled()
            }
        }

        return responseLiveData
    }


    fun fetchCocktailsByLetter(letter: Char): LiveData<List<Cocktail>> {
        val flickrRequest: Call<TheCocktailDBResponse> =
            theCocktailDBApi.fetchCocktailsByLetter(letter)
        val responseLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData()

        flickrRequest.enqueue(object : Callback<TheCocktailDBResponse> {

            override fun onFailure(call: Call<TheCocktailDBResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch cocktails", t)
            }

            override fun onResponse(
                call: Call<TheCocktailDBResponse>,
                response: Response<TheCocktailDBResponse>
            ) {
                Log.d(TAG, "Response received ${response.body()}")
                responseLiveData.value = response.body()?.drinks ?: listOf(Cocktail())
            }

        })

        return responseLiveData
    }
}