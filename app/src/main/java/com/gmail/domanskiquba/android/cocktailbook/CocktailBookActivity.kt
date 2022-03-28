package com.gmail.domanskiquba.android.cocktailbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CocktailBookActivity : AppCompatActivity() {
    var cocktailsList: List<Cocktail> = listOf(Cocktail())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_book)

        val cocktailsListLiveData = TheCocktailDBFetcher().fetchCocktailsList()
        cocktailsListLiveData.observe(this) {
            cocktailsList = it
        }
    }
}