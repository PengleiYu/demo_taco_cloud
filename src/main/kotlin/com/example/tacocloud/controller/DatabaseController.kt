package com.example.tacocloud.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.sql.DataSource

@RestController
class DatabaseController(private val db: DataSource) {
    @GetMapping("/db-url")
    fun getDatabaseUrl(): String = db.connection.metaData.url
}