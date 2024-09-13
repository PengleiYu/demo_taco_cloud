package com.example.tacocloud.controller

import com.example.tacocloud.bean.Taco
import com.example.tacocloud.bean.TacoOrder
import com.example.tacocloud.model.IngredientRepository
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController(private val ingredientRepository: IngredientRepository) {
    companion object {
        private val log = LoggerFactory.getLogger(DesignTacoController::class.java)
    }

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        ingredientRepository.findAll()
            .toList()
            .groupBy { it.type }
            .forEach {
                val key = it.key ?: return@forEach
                model.addAttribute(key.name.lowercase(), it.value)
            }
    }

    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder = TacoOrder()

    @ModelAttribute(name = "taco")
    fun taco(): Taco = Taco()

    @GetMapping
    fun showDesignForm(): String = "design"

    @PostMapping
    fun processTaco(@Valid taco: Taco, errors: Errors, @ModelAttribute tacoOrder: TacoOrder): String {
        if (errors.hasErrors()) {
            return "design"
        }
        tacoOrder.addTaco(taco)
        log.info("Processing taco: {}", taco)
        return "redirect:/orders/current"
    }
}