package com.example.tacocloud.data

import com.example.tacocloud.TacoOrder
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<TacoOrder, Long>