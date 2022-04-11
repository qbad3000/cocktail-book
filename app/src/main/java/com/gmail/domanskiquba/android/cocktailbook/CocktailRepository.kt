package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.gmail.domanskiquba.android.cocktailbook.database.CocktailDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "crime-database"

class CocktailRepository(context: Context) {
    private val database : CocktailDatabase = Room.databaseBuilder(
        context.applicationContext,
        CocktailDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val cocktailDao = database.cocktailDao()
    private val ingredientDao = database.ingredientDao()

    fun getCocktailsList() : LiveData<List<Cocktail>> {
        val responseLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData()


        CoroutineScope(Dispatchers.Main).launch {
            val cocktailsList = cocktailDao.getFavourites();
            cocktailsList.forEach() {
                launch { it.ingredients.addAll(ingredientDao.getIngredientList(it.id)) }
            }


            responseLiveData.postValue(cocktailsList)
        }

        return responseLiveData;
    }

    companion object {
        private var INSTANCE: CocktailRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CocktailRepository(context)
            }
        }

        fun get(): CocktailRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}