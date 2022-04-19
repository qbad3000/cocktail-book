package com.gmail.domanskiquba.android.cocktailbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import androidx.lifecycle.viewmodel.compose.viewModel

class CocktailBookActivity : AppCompatActivity(), CocktailListRecyclerViewFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_cocktail_book)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, CocktailListRecyclerViewFragment())
                .commit()
        }
    }

    override fun onCocktailSelected() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CocktailDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

}