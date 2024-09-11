package com.example.tacocloud

import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(WebConfig::class)
class HomeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testHomePage() {
        mockMvc.perform(get("/"))
            .andExpect(
                status().isOk
            )
            .andExpect(
                view().name("home")
            )
            .andExpect(
                content().string(
                    containsString("Welcome to...")
                )
            )
    }
}