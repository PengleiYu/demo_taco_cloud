package com.example.tacocloud

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController {
    companion object {
        private val log = LoggerFactory.getLogger(OrderController::class.java)
    }

    // TODO: 这里重复了，不知道是否可以合并
    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder = TacoOrder()


    @GetMapping("/current")
    fun orderForm(): String = "orderForm"

    @PostMapping
    fun processOrder(order: TacoOrder, sessionStatus: SessionStatus): String {
        log.info("Order submitted: {}", order)
        sessionStatus.setComplete()
        return "redirect:/"
    }
}