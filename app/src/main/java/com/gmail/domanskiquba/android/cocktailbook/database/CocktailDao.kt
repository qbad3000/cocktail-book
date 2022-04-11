package com.gmail.domanskiquba.android.cocktailbook.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.Ingredient

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktail")
    suspend fun getFavourites(): List<Cocktail>

    @Delete
    fun delete(cocktail: Cocktail)
}

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient WHERE cocktailId=:cocktailId")
    fun getIngredientList(cocktailId: String): List<Ingredient>

    @Query("DELETE FROM ingredient WHERE cocktailId=:cocktailId")
    fun deleteByCocktailId(cocktailId: String)
}