package com.example.tacocloud.data

import com.example.tacocloud.TacoOrder
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.Date

interface OrderRepository : CrudRepository<TacoOrder, Long> {
    fun findByDeliveryZip(deliveryZip: String): List<TacoOrder>

    // 这些自定义方法其实是小型DSL，SpringData可以解析含义
    fun readTacoOrdersByDeliveryZipAndPlacedAtBetween(
        deliveryZip: String,
        startDate: Date,
        endDate: Date
    ): List<TacoOrder>

    // 明确查询内容
    @Query("select  o from TacoOrder o  where o.deliveryCity='Seattle'")
    fun readTacoOrdersByDeliveryCityEqualsSeattle()
}