package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Module
import com.example.model.Modules
import com.example.model.Modules.createdAt
import com.example.model.Modules.name
import com.example.model.Modules.type
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ModuleDAOImpl : ModuleDAO {
    private fun resultRowToModule(row: ResultRow) = Module(
        id = row[Modules.id],
        name = row[name],
        type = row[type],
        createdAt = row[createdAt],
        duration = row[Modules.duration],
        status = row[Modules.status],
        description = row[Modules.description]
    )

    override suspend fun allModules(): List<Module> = dbQuery{
        Modules.selectAll().map(::resultRowToModule)
    }

    override suspend fun module(id: Int): Module?  = dbQuery{
        Modules
            .select { Modules.id eq id }
            .map(::resultRowToModule)
            .singleOrNull()
    }

    override suspend fun addNewModule(
        name: String,
        type: String,
        createdAt: String,
        duration: Int,
        status: String,
        description: String
    ): Module  = dbQuery {
        val insertStatement = Modules.insert {
            it[Modules.name] = name
            it[Modules.type] = type
            it[Modules.createdAt] = createdAt
            it[Modules.duration] = duration
            it[Modules.status] = status
            it[Modules.description] = description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModule)!!
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
        Modules.update({ Modules.id eq id }){
            it[Modules.name] = name
            it[Modules.type] = type
            it[Modules.createdAt] = createdAt
            it[Modules.duration] = duration
            it[Modules.status] = status
            it[Modules.description] = description
        } > 0
    }

    override suspend fun deleteModule(id: Int): Boolean  = dbQuery{
        Modules.deleteWhere { Modules.id eq id } > 0
    }

}
