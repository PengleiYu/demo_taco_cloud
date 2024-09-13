package com.example.tacocloud.data

import com.example.tacocloud.Ingredient
import org.springframework.data.repository.CrudRepository

interface IngredientRepository : CrudRepository<Ingredient, String>