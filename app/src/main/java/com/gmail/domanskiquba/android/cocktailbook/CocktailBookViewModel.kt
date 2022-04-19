package com.gmail.domanskiquba.android.cocktailbook

import androidx.lifecycle.*
import com.gmail.domanskiquba.android.cocktailbook.api.TheCocktailDBApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CocktailBookViewModel : ViewModel() {
    private val cocktails = mutableListOf<Cocktail>()
    init {
        CoroutineScope(Dispatchers.Main).launch {
            val list = TheCocktailDBFetcher().fetchCocktailsList()
                .filterNot { it.id in cocktails.map{ it.id } }
            cocktails.addAll(list)
            cocktails.shuffle()
            expose()
        }
        CoroutineScope(Dispatchers.Main).launch {
            val favourites = CocktailRepository.get().getFavourites()
            cocktails.filterNot { it.id in favourites.map { it.id } }
            cocktails.addAll(favourites)
            cocktails.shuffle()
            expose()
        }
    }


    var selectedTab: Int = 0
        set(value) {
            field = value
            expose()
        }

    var exposedList = MutableLiveData<List<Cocktail>>().apply { setValue(emptyList()) }

    private fun expose() {
        if(selectedTab == 0)
            exposedList.value = cocktails.filterNot{ it.favourite }
        if(selectedTab == 1)
            exposedList.value = cocktails.filter { it.favourite }.sortedBy { it.drinkName }
        if(selectedTab == 2)
            exposedList.value = cocktails.filter {it.drinkName?.contains(searchPhrase, true) ?: false}
    }

    fun favouriteSwitch(cocktail: Cocktail){
        if(cocktail.favourite) {
            cocktail.favourite = false
            expose()
            CocktailRepository.get().deleteCocktailFromDatabase(cocktail)
        }
        else {
            cocktail.favourite = true
            CocktailRepository.get().saveCocktail(cocktail)
        }
    }

    val selected = MutableLiveData<Cocktail>()
    fun select(cocktail: Cocktail) {
        selected.value = cocktail
    }

    fun search(phrase: String): List<Cocktail> {
        return cocktails.filter {it.drinkName?.contains(searchPhrase, true) ?: false}
    }
    var searchPhrase = ""
        set(value) {
            field = value
            expose()
        }
}