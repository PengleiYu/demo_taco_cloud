package com.example.tacocloud

import com.example.tacocloud.data.IngredientRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.*


@SpringBootApplication
class TacoCloudApplication {
    @Bean
    fun dataLoader(repo: IngredientRepository): CommandLineRunner? {
        // it是启动时的命令行原始参数
        return CommandLineRunner {
            println("commandLine: ${Arrays.toString(it)}")
            repo.save(Ingredient("FLTO", "Flour Tortilla", Type.WRAP))
            repo.save(Ingredient("COTO", "Corn Tortilla", Type.WRAP))
            repo.save(Ingredient("GRBF", "Ground Beef", Type.PROTEIN))
            repo.save(Ingredient("CARN", "Carnitas", Type.PROTEIN))
            repo.save(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES))
            repo.save(Ingredient("LETC", "Lettuce", Type.VEGGIES))
            repo.save(Ingredient("CHED", "Cheddar", Type.CHEESE))
            repo.save(Ingredient("JACK", "Monterrey Jack", Type.CHEESE))
            repo.save(Ingredient("SLSA", "Salsa", Type.SAUCE))
            repo.save(Ingredient("SRCR", "Sour Cream", Type.SAUCE))
        }
    }

    @Bean
    fun appLoader(): ApplicationRunner {
        // it是应用启动后已解析的参数
        return ApplicationRunner {
            println("application: ${it.optionNames}")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TacoCloudApplication>(*args)
}
