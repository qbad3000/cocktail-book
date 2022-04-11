package com.gmail.domanskiquba.android.cocktailbook

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Ingredient(
    val name: String,
    val measure: String,
    val cocktailId: String
) {
    @PrimaryKey val id = UUID.randomUUID().toString()
}

@Entity
data class Cocktail(
    @PrimaryKey val id: String,
    val drinkName: String?,
    val alcoholic: String?,
    val glass: String?,
    val instructions: String?,
    val thumbnail: String?,
    @Ignore val ingredients: MutableList<Ingredient>,
) {
    var favourite = false;

    constructor() : this(
        UUID.randomUUID().toString(),
        null, null, null, null, null, mutableListOf<Ingredient>()
    )
}