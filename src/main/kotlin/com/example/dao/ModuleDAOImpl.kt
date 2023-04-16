package com.example.dao

import com.example.enums.Status
import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Module
import com.example.model.Modules
import com.example.model.Modules.createdAt
import com.example.model.Modules.name
import com.example.model.Modules.type
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import com.example.enums.Status.Companion.valueOf
import com.example.enums.toType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import kotlin.system.measureTimeMillis

class ModuleDAOImpl : ModuleDAO {
    private fun resultRowToModule(row: ResultRow) = Module(
        id = row[Modules.moduleId],
        name = row[name],
        type = toType(row[type]),
        createdAt = row[createdAt],
        duration = row[Modules.duration],
        status = valueOf(row[Modules.status]),
        description = row[Modules.description]
    )

    // 1
    override suspend fun getCountOfRowsForFilter(whereCl: Op<Boolean>): Long = dbQuery {
        Modules.select(whereCl).count()
    }

    override suspend fun getWithFilterAndPagination(offset: Long, limit: Int, whereCl: Op<Boolean> ): List<Module> = dbQuery {
        Modules.select(whereCl).limit(limit, offset).map(::resultRowToModule)
    }

    // 2
    override suspend fun getModulesWithFilters(filter: Map<String, Any>): List<Module> = dbQuery {
        Modules.select(getFiltering(filter)).map(::resultRowToModule)
    }
    override suspend fun getWithPagination (moduleList: List<Module>, offset: Long, limit: Int): List<Module> = dbQuery{
        val endIndex = minOf((offset + limit).toInt(), moduleList.size)
        moduleList.subList(offset.toInt(), endIndex)
    }


    override suspend fun getFiltering(filter: Map<String, Any>): Op<Boolean> {
        var whereClause: Op<Boolean>? = null
        whereClause = filter.entries.fold(null as Op<Boolean>?) { acc, entry ->
            val (field, value) = entry
            when (field) {
                "moduleId" -> acc?.and(Modules.moduleId eq value as Int) ?: (Modules.moduleId eq value as Int)
                "name" -> acc?.and(name like "$value%") ?: (name like "$value%")
                "type" -> acc?.and(type eq value as String) ?: (type eq value as String)
                "createdAt" -> acc?.and(createdAt eq value as String) ?: (createdAt eq value as String)
                "duration" -> acc?.and(Modules.duration eq value as Int) ?: (Modules.duration eq value as Int)
                "status" -> acc?.and(Modules.status eq value as String) ?: (Modules.status eq value as String)
                "description" -> acc?.and(Modules.description eq value as String) ?: (Modules.description eq value as String)
                else -> throw IllegalArgumentException("Invalid filter field: $field")
            }
        }
        return whereClause ?: Op.TRUE
    }




    override suspend fun module(id: Int): Module?  = dbQuery{
        Modules
            .select { Modules.moduleId eq id }
            .map(::resultRowToModule)
            .singleOrNull()
    }

    override suspend fun addNewModule(
        name: String,
        type: String,
        createdAt: String,
        duration: Int,
        status: Status,
        description: String
    ): Module  = dbQuery {
        val insertStatement = Modules.insert {
            it[Modules.name] = name
            it[Modules.type] = type
            it[Modules.createdAt] = createdAt
            it[Modules.duration] = duration
            it[Modules.status] = status.name
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
        status: Status,
        description: String
    ): Boolean  = dbQuery {
        Modules.update({ Modules.moduleId eq id }){
            it[Modules.name] = name
            it[Modules.type] = type
            it[Modules.createdAt] = createdAt
            it[Modules.duration] = duration
            it[Modules.status] = status.name
            it[Modules.description] = description
        } > 0
    }

    override suspend fun deleteModule(id: Int): Boolean  = dbQuery{
        Modules.deleteWhere { moduleId eq id } > 0
    }



}
