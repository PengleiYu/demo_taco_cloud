package com.example.tacocloud

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.CreditCardNumber
import java.io.Serializable
import java.util.*

enum class Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
}

// JPA会自动根据@Entity注解生成表，不需要schema.sql了
@Entity
data class Ingredient(
    @field:Id
    val id: String? = null,
    val name: String? = null,
    val type: Type? = null,
)

@Entity
data class Taco(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @field:NotNull
    @field:Size(min = 5, message = "name必须至少5个字符")
    var name: String? = null,
    @field:NotNull
    @field:Size(min = 1, message = "必须选择至少1个配料")
    @field:ManyToMany// 该注解会自动生成二者的关联表，不需要手动定义了
    var ingredients: MutableList<Ingredient>? = mutableListOf(),
    var createdAt: Date = Date(),
)

@Entity
data class TacoOrder(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
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
    @field:OneToMany(cascade = [CascadeType.ALL])//必须，这会让Taco表产生TacoOrder表的外键
    val tacos: MutableList<Taco> = mutableListOf(),
    var placedAt: Date = Date(),
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    fun addTaco(taco: Taco) {
        tacos += taco
    }
}