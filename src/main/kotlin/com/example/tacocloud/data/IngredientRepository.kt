package com.example.tacocloud.data

import com.example.tacocloud.Ingredient
import java.util.Optional

interface IngredientRepository {
    fun findAll(): Iterable<Ingredient>

    // TODO: 是否可使用kt可null类型
    fun findById(id: String): Ingredient?

    fun save(ingredient: Ingredient): Ingredient
}