package com.gmail.domanskiquba.android.cocktailbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "TheCocktailDBFetcher"

class TheCocktailDBFetcher {

    private val theCocktailDBApi: TheCocktailDBApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        theCocktailDBApi = retrofit.create(TheCocktailDBApi::class.java)
    }

    fun fetchCocktailsByLetter() {
        val flickrRequest: Call<String> = theCocktailDBApi.fetchCocktailsByLetter()

        flickrRequest.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch cocktails", t)
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d(TAG, "Response received ${response.body()}")
            }
        })
    }
}