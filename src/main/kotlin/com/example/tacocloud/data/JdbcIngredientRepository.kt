package com.example.tacocloud.data

import com.example.tacocloud.Ingredient
import com.example.tacocloud.Type
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcIngredientRepository(private val jdbcTemplate: JdbcTemplate) : IngredientRepository {
    override fun findAll(): Iterable<Ingredient> =
        jdbcTemplate.query("select id, name, type from Ingredient", rowMapper)

    override fun findById(id: String): Ingredient? =
        jdbcTemplate.query("select id, name, type from Ingredient where id = ?", rowMapper, id)
            .getOrNull(0)

    override fun save(ingredient: Ingredient): Ingredient = ingredient.apply {
        jdbcTemplate.update("insert into Ingredient (id,name,type) values(?,?,?)", id, name, type.toString())
    }

    private val rowMapper = RowMapper<Ingredient> { rs, _ ->
        with(rs) {
            val id = getString("id") ?: ""
            val name = getString("name") ?: ""
            val type = getString("type") ?: ""
            Ingredient(id, name, Type.valueOf(type))
        }
    }
}