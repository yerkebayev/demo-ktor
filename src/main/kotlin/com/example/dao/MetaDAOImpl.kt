package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Meta
import com.example.model.Metas
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class MetaDAOImpl : MetaDAO {
    private fun resultRowToMeta(row: ResultRow) = Meta(
        id = row[Metas.id],
        moduleId = row[Metas.moduleId],
        metaKey = row[Metas.metaKey],
        metaValue = row[Metas.metaValue]
    )

    override suspend fun getMetasWithFilters(filter: Map<String, Any>, moduleId: Int): List<Meta> = dbQuery{
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
        }.map(::resultRowToMeta)
    }

    override suspend fun getWithPagination(metaList: List<Meta>, offset: Long, limit: Int ): List<Meta> = dbQuery {
        val endIndex = minOf((offset + limit).toInt(), metaList.size)
        metaList.subList(offset.toInt(), endIndex)
    }


    override suspend fun meta(id: Int): Meta? = dbQuery {
        Metas.select { Metas.id eq id }.map(::resultRowToMeta).singleOrNull()
    }

    override suspend fun addNewMeta(moduleId: Int, metaKey: String, metaValue: String) = dbQuery {
        val statement = Metas.insert {
            it[Metas.moduleId] = moduleId
            it[Metas.metaKey] = metaKey
            it[Metas.metaValue] = metaValue
        }
        statement.resultedValues?.singleOrNull()?.let(::resultRowToMeta)
    }

    override suspend fun editMeta(id: Int, moduleId: Int, metaKey: String, metaValue: String): Boolean =
        dbQuery {
            Metas.update({ Metas.id eq id }) {
                it[Metas.id] = id
                it[Metas.moduleId] = moduleId
                it[Metas.metaKey] = metaKey
                it[Metas.metaValue] = metaValue
            } > 0
        }

    override suspend fun deleteMeta(id: Int): Boolean = dbQuery {
        Metas.deleteWhere { Metas.id eq id } > 0
    }



}