package com.gmail.domanskiquba.android.cocktailbook

import java.util.*

data class Ingredient(
    val name: String,
    val measure: String,
)

data class Cocktail(
    val id: String,
    val drinkName: String?,
    val alcoholic: String?,
    val glass: String?,
    val instructions: String?,
    val thumbnail: String?,
    val ingredients: List<Ingredient>,
) {
    var favourite = false;

    constructor() : this(
        UUID.randomUUID().toString(),
        null, null, null, null, null, emptyList()
    )
}