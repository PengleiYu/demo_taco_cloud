package com.example.tacocloud

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class IngredientByIdConverter : Converter<String, Ingredient> {
    private val ingredientMap = INGREDIENT_LIST.groupBy { it.id }
    override fun convert(source: String): Ingredient? {
        return ingredientMap[source]?.firstOrNull()
    }
}