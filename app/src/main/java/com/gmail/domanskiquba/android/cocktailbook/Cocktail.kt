package com.gmail.domanskiquba.android.cocktailbook

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Ingredient(
    var name: String,
    var measure: String,
    var cocktailId: String
) {
    @PrimaryKey var id = UUID.randomUUID().toString()
}

@Entity
data class Cocktail(
    @PrimaryKey var id: String,
    var drinkName: String?,
    var alcoholic: String?,
    var glass: String?,
    var instructions: String?,
    var thumbnail: String?,
    @Ignore var ingredients: MutableList<Ingredient>,
) {
    var favourite = false;

    constructor() : this(
        UUID.randomUUID().toString(),
        null, null, null, null, null, mutableListOf<Ingredient>()
    )
}