package com.gmail.domanskiquba.android.cocktailbook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.Ingredient

@Database(entities = [Cocktail::class, Ingredient::class], version=1)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao() : CocktailDao
    abstract fun ingredientDao() : IngredientDao
}