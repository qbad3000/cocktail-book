package com.gmail.domanskiquba.android.cocktailbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CocktailBookActivity : AppCompatActivity() {
    var cocktailsList: List<Cocktail> = listOf(Cocktail())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_book)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, CocktailListRecyclerViewFragment.newInstance())
                .commit()
        }
    }
}