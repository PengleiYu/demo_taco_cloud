package com.example.tacocloud

import com.example.tacocloud.data.OrderRepository
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import java.util.*

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController(private val orderRepository: OrderRepository) {
    companion object {
        private val log = LoggerFactory.getLogger(OrderController::class.java)
    }

    // TODO: 这里重复了，不知道是否可以合并
    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder = TacoOrder()


    @GetMapping("/current")
    fun orderForm(): String = "orderForm"

    @PostMapping
    fun processOrder(@Valid order: TacoOrder, errors: Errors, sessionStatus: SessionStatus): String {
        if (errors.hasErrors()) {
            return "orderForm"
        }
        order.placedAt = Date()
        order.tacos.forEach { it.createdAt = Date() }
        orderRepository.save(order)
        log.info("Order submitted: {}", order)
        sessionStatus.setComplete()
        return "redirect:/"
    }
}