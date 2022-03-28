package com.gmail.domanskiquba.android.cocktailbook.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCocktailDBApi {
    @GET( "1" +
            "/search.php")
    suspend fun fetchCocktailsByLetter(@Query("f") letter: Char): TheCocktailDBResponse
}