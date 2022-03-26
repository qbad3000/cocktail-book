package com.gmail.domanskiquba.android.cocktailbook.api

import retrofit2.Call
import retrofit2.http.GET

interface TheCocktailDBApi {
    @GET( "1" +
            "/search.php?f=a")
    fun fetchCocktailsByLetter(): Call<String>
}