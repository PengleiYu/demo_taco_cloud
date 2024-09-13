package com.example.tacocloud.data

import com.example.tacocloud.IngredientRef
import com.example.tacocloud.Taco
import com.example.tacocloud.TacoOrder
import org.springframework.asm.Type
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.PreparedStatementCreatorFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types
import java.util.*

@Repository
class JdbcOrderRepository(private val jdbcOperations: JdbcOperations) : OrderRepository {
    @Transactional
    override fun save(order: TacoOrder): TacoOrder {
        order.placeAt = Date()
        val factory = PreparedStatementCreatorFactory(
            """
                insert into TACO_ORDER (delivery_name, delivery_street, delivery_city,
                 delivery_state, delivery_zip, cc_number, cc_expiration,
                  cc_cvv, placed_at) 
                  values (?,?,?,?,?,?,?,?,?)
            """.trimIndent(),
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.TIMESTAMP,
        ).apply { setReturnGeneratedKeys(true) }
        val creator = with(order) {
            factory.newPreparedStatementCreator(
                listOf(
                    deliveryName, deliveryStreet, deliveryCity,
                    deliveryState, deliveryZip, ccNumber, ccExpiration,
                    ccCVV, placeAt
                )
            )
        }
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(creator, keyHolder)
        val orderId = keyHolder.key!!.toLong()
        order.id = orderId

        order.tacos.forEachIndexed { index, taco ->
            saveTaco(orderId, index, taco)
        }

        return order
    }

    private fun saveTaco(orderId: Long, orderKey: Int, taco: Taco): Long {
        taco.createdAt = Date()
        val factory = PreparedStatementCreatorFactory(
            """
                insert into TACO (NAME, TACO_ORDER, TACO_ORDER_KEY, CREATED_AT) values ( ?,?,?,? )
            """.trimIndent(),
            Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG,
        ).apply { setReturnGeneratedKeys(true) }
        val creator = factory.newPreparedStatementCreator(
            listOf(taco.name, taco.createdAt, orderId, orderKey)
        )
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(creator, keyHolder)
        val tacoId = keyHolder.key!!.toLong()
        taco.id = tacoId

        saveIngredientRefs(tacoId, taco.ingredients)
        return tacoId
    }

    private fun saveIngredientRefs(tacoId: Long, ingredientRefs: List<IngredientRef>) {
        ingredientRefs.forEachIndexed { index, ref ->
            jdbcOperations.update(
                """
                   insert into INGREDIENT_REF (INGREDIENT, TACO, TACO_KEY) values ( ?,?,? )
               """.trimIndent(),
                ref.ingredient, tacoId, index
            )
        }
    }
}