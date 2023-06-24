package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import androidx.room.Room
import com.gmail.domanskiquba.android.cocktailbook.database.CocktailDatabase
import kotlinx.coroutines.*

private const val DATABASE_NAME = "crime-database"

class CocktailRepository(context: Context) {
    private val database : CocktailDatabase = Room.databaseBuilder(
        context.applicationContext,
        CocktailDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val cocktailDao = database.cocktailDao()
    private val ingredientDao = database.ingredientDao()

    suspend fun getFavourites() : List<Cocktail>  {
        val cocktailsList = cocktailDao.getFavourites()
        cocktailsList.map {
            CoroutineScope(Dispatchers.Main).async {
                        it.ingredients.addAll(ingredientDao.getIngredientList(it.id))
            }
        }.awaitAll()
        return cocktailsList
    }

    fun saveCocktail(cocktail: Cocktail) {
        CoroutineScope(Dispatchers.Main).launch {
            launch{ cocktailDao.addCocktail(cocktail) }
            cocktail.ingredients.forEach() {
                launch {ingredientDao.addIngredient(it)}
            }

        }
    }

    fun deleteCocktailFromDatabase(cocktail: Cocktail) {
        CoroutineScope(Dispatchers.Main).launch {
            launch{ cocktailDao.delete(cocktail) }
            launch{ ingredientDao.deleteByCocktailId(cocktail.id)}
        }
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