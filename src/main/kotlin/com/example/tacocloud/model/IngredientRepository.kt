package com.example.tacocloud.model

import com.example.tacocloud.bean.Ingredient
import org.springframework.data.repository.CrudRepository

interface IngredientRepository : CrudRepository<Ingredient, String>