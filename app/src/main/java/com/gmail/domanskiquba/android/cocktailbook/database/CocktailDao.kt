package com.gmail.domanskiquba.android.cocktailbook.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.Ingredient

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktail")
    suspend fun getFavourites(): List<Cocktail>

    @Insert
    suspend fun addCocktail(cocktail: Cocktail)

    @Delete
    suspend fun delete(cocktail: Cocktail)
}

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient WHERE cocktailId=:cocktailId")
    suspend fun getIngredientList(cocktailId: String): List<Ingredient>

    @Insert
    suspend fun addIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredient WHERE cocktailId=:cocktailId")
    suspend fun deleteByCocktailId(cocktailId: String)
}