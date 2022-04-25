package com.gmail.domanskiquba.android.cocktailbook

import com.gmail.domanskiquba.android.cocktailbook.api.DrinkDeserializer
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBApi
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheCocktailDBFetcher {

    private val theCocktailDBApi: TheCocktailDBApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/")
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapter(Cocktail::class.java, DrinkDeserializer()).create()))
            .build()

        theCocktailDBApi = retrofit.create(TheCocktailDBApi::class.java)
    }

    suspend fun fetchCocktailsList(): List<Cocktail> {
        val responses = ('a'..'z').map { letter ->
            CoroutineScope(Dispatchers.Main)
                .async { theCocktailDBApi.fetchCocktailsByLetter(letter) }
        }.awaitAll()

        return responses.mapNotNull { it.drinks }.flatten().filterNotNull()
    }
}