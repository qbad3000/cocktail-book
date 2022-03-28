package com.gmail.domanskiquba.android.cocktailbook

import androidx.lifecycle.*
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBApi
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        val responseLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData()

        CoroutineScope(Dispatchers.Main).launch {
            with(responseLiveData) {
                postValue(
                        ('a'..'z').map { letter ->
                            CoroutineScope(Dispatchers.Main)
                                .async { theCocktailDBApi.fetchCocktailsByLetter(letter) }
                        }
                            .map { response -> response.await() }
                            .mapNotNull { it.drinks }
                            .flatten().distinct().shuffled())
            }
        }

        return responseLiveData
    }


    fun fetchCocktailsByLetter(letter: Char): LiveData<List<Cocktail>> {
        val responseLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData()
        CoroutineScope(Dispatchers.Main).launch {
            val response = theCocktailDBApi.fetchCocktailsByLetter(letter)
            responseLiveData.postValue(response.drinks ?: listOf(Cocktail()))
        }

        return responseLiveData
    }
}