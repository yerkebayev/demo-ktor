package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Module
import com.example.model.ModuleLink
import com.example.model.ModuleLinks
import com.example.model.Modules
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class ModuleLinkDAOImpl : ModuleLinkDAO {

    private fun resultRowToModuleLink(row: ResultRow) = ModuleLink(
        id = row[ModuleLinks.id],
        parentId = row[ModuleLinks.parentId],
        childId = row[ModuleLinks.childId],
        linkType = row[ModuleLinks.linkType]
    )

    override suspend fun getModuleLinks(filter: Map<String, Any>, offset: Long, limit: Int): List<ModuleLink> = dbQuery {
        ModuleLinks.select {
            val whereClause = filter.entries.fold(null as Op<Boolean>?) { acc, entry ->
                val (field, value) = entry
                when (field) {
                    "id" -> acc?.and(ModuleLinks.id eq value as Int) ?: (ModuleLinks.id eq value as Int)
                    "parentId" -> acc?.and(ModuleLinks.parentId eq value as Int) ?: (ModuleLinks.parentId eq value as Int)
                    "childId" -> acc?.and(ModuleLinks.childId eq value as Int) ?: (ModuleLinks.childId eq value as Int)
                    "linkType" -> acc?.and(ModuleLinks.linkType eq value as String) ?: (ModuleLinks.linkType eq value as String)
                    else -> throw IllegalArgumentException("Invalid filter field: $field")
                }
            }
            whereClause ?: Op.TRUE
        }.limit(limit, offset)
            .map(::resultRowToModuleLink)
    }


    override suspend fun moduleLink(id: Int): ModuleLink? = dbQuery {
        ModuleLinks
            .select { ModuleLinks.id eq id }
            .map(::resultRowToModuleLink)
            .singleOrNull()
    }

    override suspend fun addNewModuleLink(parentId: Int, childId: Int, linkType: String): ModuleLink =
        dbQuery {
            val insertStatement = ModuleLinks.insert {
                it[ModuleLinks.parentId] = parentId
                it[ModuleLinks.childId] = childId
                it[ModuleLinks.linkType] = linkType
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModuleLink)!!
        }

    override suspend fun editModuleLink(id: Int, parentId: Int, childId: Int, linkType: String): Boolean =
        dbQuery {
            ModuleLinks.update({ ModuleLinks.id eq id }) {
                it[ModuleLinks.parentId] = parentId
                it[ModuleLinks.childId] = childId
                it[ModuleLinks.linkType] = linkType
            } > 0
        }

    override suspend fun deleteModuleLink(id: Int): Boolean = dbQuery {
        ModuleLinks.deleteWhere { ModuleLinks.id eq id } > 0
    }


}