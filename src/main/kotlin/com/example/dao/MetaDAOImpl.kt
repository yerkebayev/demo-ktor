package com.example.dao

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
    override suspend fun allMetas(id: Int): List<Meta> = DatabaseFactory.dbQuery {
        Metas.select { Metas.moduleId eq id }.map(::resultRowToMeta)
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

    override suspend fun getMetas(id: Int, offset: Long, limit: Int): List<Meta> = DatabaseFactory.dbQuery {
        Metas
            .select{ Metas.moduleId eq id }
            .limit(limit, offset)
            .map { resultRowToMeta(it) }
    }


}