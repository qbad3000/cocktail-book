package com.gmail.domanskiquba.android.cocktailbook

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CocktailBookViewModel : ViewModel() {
    private val cocktails = mutableListOf<Cocktail>()
    init {
        requestApi()

        CoroutineScope(Dispatchers.Main).launch {
            val favourites = CocktailRepository.get().getFavourites()
            cocktails.filterNot { it.id in favourites.map { it.id } }
            cocktails.addAll(favourites)
            cocktails.shuffle()
            if(selectedTab.get() == 1)
                expose()
        }
    }

    fun requestApi() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                allowRefreshButton.set(false)
                val list = TheCocktailDBFetcher().fetchCocktailsList()
                    .filterNot { it.id in cocktails.map { it.id } }
                cocktails.addAll(list)
                cocktails.shuffle()
                if( selectedTab.get() == 0 )
                    expose()
            }  catch(exception: Exception) {
                allowRefreshButton.set(true)
            }
        }
    }

    val allowRefreshButton = ObservableBoolean(false)

    var selectedTab = object : ObservableInt(0) {
        override fun set(value: Int) {
            super.set(value)
            expose()
        }
    }

    var exposedList = MutableLiveData<List<Cocktail>>().apply { setValue(emptyList()) }

    private fun expose() {
        if(selectedTab.get() == 0)
            exposedList.value = cocktails.filterNot{ it.favourite }
        if(selectedTab.get() == 1)
            exposedList.value = cocktails.filter { it.favourite }.sortedBy { it.drinkName }
        if(selectedTab.get() == 2){
            if(searchPhrase == "")
                exposedList.value = emptyList()
            else
                exposedList.value = cocktails.filter {it.drinkName?.contains(searchPhrase, true) ?: false}
                    .sortedBy { it.drinkName!!.indexOf(searchPhrase, ignoreCase = true) }
        }
    }

    var searchPhrase = ""
        set(value) {
            field = value
            expose()
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
}