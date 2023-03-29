package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Meta
import com.example.model.Metas
import com.example.model.Metas.metaKey
import com.example.model.Metas.metaValue
import com.example.model.Metas.moduleId
import com.example.model.Modules
import com.example.model.Modules.name
import com.example.model.Modules.type
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import com.example.model.Module

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToModule(row: ResultRow) = Module(
        id = row[Modules.id],
        name = row[name],
        type = row[type],
        createdAt = null.toString(),
        duration = row[Modules.duration],
        status = row[Modules.status],
        description = row[Modules.description]
    )
    private fun resultRowToMeta(row: ResultRow) = Meta(
        id = row[Metas.id],
        moduleId = row[moduleId],
        metaKey = row[metaKey],
        metaValue = row[metaValue]

    )
    override suspend fun allModules(): List<Module> = dbQuery {
        Modules.selectAll().map(::resultRowToModule)
    }

    override suspend fun module(id: Int): Module? = dbQuery {
        Modules
            .select { Modules.id eq id }
            .map(::resultRowToModule)
            .singleOrNull()
    }

    override suspend fun addNewModule(name: String,
                                      type: String,
                                      createdAt: String,
                                      duration: Int,
                                      status: String,
                                      description: String): Module? = dbQuery {
            val insertStatement = Modules.insert {
                it[Modules.name] = name
                it[Modules.type] = type
//                it[createdAt] = createdAt
                it[Modules.duration] = duration
                it[Modules.status] = status
                it[Modules.description] = description
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModule)
        }

    override suspend fun editModule(
        id: Int,
        name: String,
        type: String,
        createdAt: String,
        duration: Int,
        status: String,
        description: String
    ): Boolean  = dbQuery {
        Modules.update({ Modules.id eq id }) {
            it[Modules.name] = name
            it[Modules.type] = type
//                it[createdAt] = createdAt
            it[Modules.duration] = duration
            it[Modules.status] = status
            it[Modules.description] = description
        } > 0
    }

    override suspend fun deleteModule(id: Int): Boolean = dbQuery{
        Modules.deleteWhere { Modules.id eq id } > 0
    }

    override suspend fun allMetas(id: Int): List<Meta> = dbQuery {
        Metas.select { Metas.moduleId eq id }.map (:: resultRowToMeta)
    }

    override suspend fun meta(id: Int): Meta? = dbQuery{
        Metas.select { Metas.id eq id }.map (:: resultRowToMeta).singleOrNull()
    }

    override suspend fun addNewMeta(moduleId: Int, metaKey: String, metaValue: String) = dbQuery{
        val statement = Metas.insert {
            it[Metas.moduleId] = moduleId
            it[Metas.metaKey] = metaKey
            it[Metas.metaValue] = metaValue
        }
        statement.resultedValues?.singleOrNull()?.let(::resultRowToMeta)
    }

    override suspend fun editMeta(id: Int, moduleId: Int, metaKey: String, metaValue: String): Boolean = dbQuery {
            Metas.update({ Metas.id eq id }) {
                it[Metas.id] = id
                it[Metas.moduleId] = moduleId
                it[Metas.metaKey] = metaKey
                it[Metas.metaValue] = metaValue
            } > 0
        }

    override suspend fun deleteMeta(id: Int): Boolean = dbQuery{
        Metas.deleteWhere { Metas.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
    }
}
