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

//class Cocktail(cocktailResponse: TheSingleCocktailResponse) {
//    val id = cocktailResponse.idDrink ?: UUID.randomUUID().toString()
//    var drinkName = cocktailResponse.strDrink ?: ""
//    var alcoholic = cocktailResponse.strAlcoholic ?: "";
//    var glass = cocktailResponse.strGlass ?: "";
//    var instructions = cocktailResponse.strInstructions;
//    var thumbnail = cocktailResponse.strDrinkThumb ?: "";
//    val ingredientsList = mutableListOf(
//        Ingredient(cocktailResponse.strIngredient1, cocktailResponse.strMeasure1),
//        Ingredient(cocktailResponse.strIngredient2, cocktailResponse.strMeasure2),
//        Ingredient(cocktailResponse.strIngredient3, cocktailResponse.strMeasure3),
//        Ingredient(cocktailResponse.strIngredient4, cocktailResponse.strMeasure4),
//        Ingredient(cocktailResponse.strIngredient5, cocktailResponse.strMeasure5),
//        Ingredient(cocktailResponse.strIngredient6, cocktailResponse.strMeasure6),
//        Ingredient(cocktailResponse.strIngredient7, cocktailResponse.strMeasure7),
//        Ingredient(cocktailResponse.strIngredient8, cocktailResponse.strMeasure8),
//        Ingredient(cocktailResponse.strIngredient9, cocktailResponse.strMeasure9),
//        Ingredient(cocktailResponse.strIngredient10, cocktailResponse.strMeasure10),
//        Ingredient(cocktailResponse.strIngredient11, cocktailResponse.strMeasure11),
//        Ingredient(cocktailResponse.strIngredient12, cocktailResponse.strMeasure12),
//        Ingredient(cocktailResponse.strIngredient13, cocktailResponse.strMeasure13),
//        Ingredient(cocktailResponse.strIngredient14, cocktailResponse.strMeasure14),
//        Ingredient(cocktailResponse.strIngredient15, cocktailResponse.strMeasure15)
//    ).filterNot { it.ingredientName == "" }
//    var favourite = false;
//
//    constructor() : this(TheSingleCocktailResponse()) { }
//
//    inner class Ingredient(_ingredientName: String?, _measure: String?) {
//            var ingredientName = _ingredientName ?: "";
//            var measure = _measure ?: "";
//    }
//}