package com.example.tacocloud

import com.example.tacocloud.data.IngredientRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull


@Component
class IngredientByIdConverter(private val ingredientRepository: IngredientRepository) : Converter<String, Ingredient> {
    override fun convert(id: String): Ingredient? = ingredientRepository.findById(id).getOrNull()
}