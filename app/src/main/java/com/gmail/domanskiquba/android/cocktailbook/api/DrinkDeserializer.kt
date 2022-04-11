package com.gmail.domanskiquba.android.cocktailbook.api

import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.Ingredient
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*


class DrinkDeserializer : JsonDeserializer<Cocktail> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Cocktail? {
        val drinkObject = json.asJsonObject

        return Cocktail(
            drinkObject.get("idDrink").takeUnless { it is JsonNull }?.asString ?: UUID.randomUUID().toString(),
            drinkObject.get("strDrink").takeUnless { it is JsonNull }?.asString,
            drinkObject.get("strAlcoholic").takeUnless { it is JsonNull }?.asString,
            drinkObject.get("strGlass").takeUnless { it is JsonNull }?.asString,
            drinkObject.get("strInstructions").takeUnless { it is JsonNull }?.asString,
            drinkObject.get("strDrinkThumb").takeUnless { it is JsonNull }?.asString,
            (1..15).mapNotNull { i ->
                val name = drinkObject.get("strIngredient$i").takeUnless { it is JsonNull }?.asString
                val measure = drinkObject.get("strMeasure$i").takeUnless { it is JsonNull }?.asString
                if (name != null && measure != null) {
                    Ingredient(name, measure)
                } else null
            }
        )
    }
}