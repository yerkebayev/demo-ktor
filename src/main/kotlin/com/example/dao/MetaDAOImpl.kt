package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Meta
import com.example.model.Metas
import com.example.model.Modules
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class MetaDAOImpl : MetaDAO {
    private fun resultRowToMeta(row: ResultRow) = Meta(
        id = row[Metas.id],
        moduleId = row[Metas.moduleId],
        metaKey = row[Metas.metaKey],
        metaValue = row[Metas.metaValue]
    )

    override suspend fun getMetas(filter: Map<String, Any>, moduleId: Int, offset: Long, limit: Int): List<Meta> = dbQuery{
        Metas.select {
            val whereClause = filter.entries.fold(null as Op<Boolean>?) { acc, entry ->
                val (field, value) = entry
                when (field) {
                    "metaId" -> acc?.and(Metas.id eq value as Int) ?: (Metas.id eq value as Int)
                    "moduleId" -> acc?.and(Metas.moduleId eq value as Int) ?: (Metas.moduleId eq value as Int)
                    "metaKey" -> acc?.and(Metas.metaKey like "$value%") ?: (Metas.metaKey like "$value%")
                    "metaValue" -> acc?.and(Metas.metaValue like "$value%") ?: (Metas.metaValue like "$value%")
                    else -> throw IllegalArgumentException("Invalid filter field: $field")
                }
            }
            whereClause?.and(Metas.moduleId eq moduleId) ?: (Metas.moduleId eq moduleId)
        }.limit(limit, offset)
            .map(::resultRowToMeta)
    }



    override suspend fun meta(id: Int): Meta? = DatabaseFactory.dbQuery {
        Metas.select { Metas.id eq id }.map(::resultRowToMeta).singleOrNull()
    }

    override suspend fun addNewMeta(moduleId: Int, metaKey: String, metaValue: String) = DatabaseFactory.dbQuery {
        val statement = Metas.insert {
            it[Metas.moduleId] = moduleId
            it[Metas.metaKey] = metaKey
            it[Metas.metaValue] = metaValue
        }
        statement.resultedValues?.singleOrNull()?.let(::resultRowToMeta)
    }

    override suspend fun editMeta(id: Int, moduleId: Int, metaKey: String, metaValue: String): Boolean =
        DatabaseFactory.dbQuery {
            Metas.update({ Metas.id eq id }) {
                it[Metas.id] = id
                it[Metas.moduleId] = moduleId
                it[Metas.metaKey] = metaKey
                it[Metas.metaValue] = metaValue
            } > 0
        }

    override suspend fun deleteMeta(id: Int): Boolean = DatabaseFactory.dbQuery {
        Metas.deleteWhere { Metas.id eq id } > 0
    }



}