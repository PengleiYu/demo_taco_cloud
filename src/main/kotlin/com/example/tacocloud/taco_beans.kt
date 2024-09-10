package com.example.tacocloud

enum class Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
}

data class Ingredient(val id: String, val name: String, val type: Type)

data class Taco(
    var name: String? = null, var ingredients: List<Ingredient> = listOf()
)

data class TacoOrder(
    var deliveryName: String? = null,
    var deliveryStreet: String? = null,
    var deliveryCity: String? = null,
    var deliveryState: String? = null,
    var deliveryZip: String? = null,
    var ccNumber: String? = null,
    var ccExpiration: String? = null,
    var ccCVV: String? = null,
    val tacos: MutableList<Taco> = mutableListOf()
) {
    fun addTaco(taco: Taco) {
        tacos += taco
    }
}