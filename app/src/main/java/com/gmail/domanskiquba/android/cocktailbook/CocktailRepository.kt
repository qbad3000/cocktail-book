package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getCocktailsList() : LiveData<List<Cocktail>> {
        val responseLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData()

        CoroutineScope(Dispatchers.Main).launch {
                val cocktailsListDbDeferred = async{cocktailDao.getFavourites()}
                val cocktailsListApiDeferred = async{ TheCocktailDBFetcher().fetchCocktailsList() }

                val cocktailsListDb = cocktailsListDbDeferred.await()
                cocktailsListDb.map {
                    async {
                        it.ingredients.addAll(ingredientDao.getIngredientList(it.id))
                    }
                }.awaitAll();

                responseLiveData.postValue(cocktailsListDb);

                val cocktailsListApi = cocktailsListApiDeferred.await()
                    .filterNot { cocktailsListDb.contains(it) }.shuffled();

                responseLiveData.postValue(cocktailsListDb + cocktailsListApi);
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