package com.example.tacocloud

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.CreditCardNumber

enum class Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
}

data class Ingredient(val id: String, val name: String, val type: Type)

data class Taco(
    @field:NotNull
    @field:Size(min = 5, message = "name必须至少5个字符")
    var name: String? = null,
    @field:NotNull
    @field:Size(min = 1, message = "必须选择至少1个配料")
    var ingredients: List<Ingredient> = listOf()
)

data class TacoOrder(
    @field:NotBlank(message = "投递地址是必填的")
    var deliveryName: String? = null,
    @field:NotBlank(message = "投递街道是必填的")
    var deliveryStreet: String? = null,
    @field:NotBlank(message = "投递城市是必填的")
    var deliveryCity: String? = null,
    @field:NotBlank(message = "投递国家是必填的")
    var deliveryState: String? = null,
    @field:NotBlank(message = "投递邮编是必填的")
    var deliveryZip: String? = null,
    @field:CreditCardNumber(message = "不是合法的信用卡号")
    var ccNumber: String? = null,
    @field:Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])\$", message = "格式必须为MM/YY")
    var ccExpiration: String? = null,
    @field:Digits(integer = 3, fraction = 0, message = "非法的CVV")
    var ccCVV: String? = null,
    val tacos: MutableList<Taco> = mutableListOf()
) {
    fun addTaco(taco: Taco) {
        tacos += taco
    }
}