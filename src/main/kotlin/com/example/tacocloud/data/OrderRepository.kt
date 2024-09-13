package com.example.tacocloud.data

import com.example.tacocloud.TacoOrder

interface OrderRepository {
    fun save(order: TacoOrder): TacoOrder
}