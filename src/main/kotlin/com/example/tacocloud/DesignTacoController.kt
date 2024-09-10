package com.example.tacocloud

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import java.util.*

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController {
    companion object {
        private val log = LoggerFactory.getLogger(DesignTacoController::class.java)
    }

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        INGREDIENT_LIST.groupBy { it.type }
            .forEach { model.addAttribute(it.key.name.lowercase(), it.value) }
    }

    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder = TacoOrder()

    @ModelAttribute(name = "taco")
    fun taco(): Taco = Taco()

    @GetMapping
    fun showDesignForm(): String = "design"

    @PostMapping
    fun processTaco(taco: Taco, @ModelAttribute tacoOrder: TacoOrder): String {
        tacoOrder.addTaco(taco)
        log.info("Processing taco: {}", taco)
        return "redirect:/orders/current"
    }
}