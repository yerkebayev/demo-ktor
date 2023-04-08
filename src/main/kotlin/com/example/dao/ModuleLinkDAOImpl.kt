package com.example.dao

import com.example.model.ModuleLink
import com.example.model.ModuleLinks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ModuleLinkDAOImpl : ModuleLinkDAO {

    private fun resultRowToModuleLink(row: ResultRow) = ModuleLink(
        id = row[ModuleLinks.id],
        parentId = row[ModuleLinks.parentId],
        childId = row[ModuleLinks.childId],
        linkType = row[ModuleLinks.linkType]
    )

    override suspend fun allModuleLinks(): List<ModuleLink> = DatabaseFactory.dbQuery {
        ModuleLinks.selectAll().map(::resultRowToModuleLink)
    }

    override suspend fun moduleLink(id: Int): ModuleLink? = DatabaseFactory.dbQuery {
        ModuleLinks
            .select { ModuleLinks.id eq id }
            .map(::resultRowToModuleLink)
            .singleOrNull()
    }

    override suspend fun addNewModuleLink(parentId: Int, childId: Int, linkType: String): ModuleLink =
        DatabaseFactory.dbQuery {
            val insertStatement = ModuleLinks.insert {
                it[ModuleLinks.parentId] = parentId
                it[ModuleLinks.childId] = childId
                it[ModuleLinks.linkType] = linkType
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModuleLink)!!
        }

    override suspend fun editModuleLink(id: Int, parentId: Int, childId: Int, linkType: String): Boolean =
        DatabaseFactory.dbQuery {
            ModuleLinks.update({ ModuleLinks.id eq id }) {
                it[ModuleLinks.parentId] = parentId
                it[ModuleLinks.childId] = childId
                it[ModuleLinks.linkType] = linkType
            } > 0
        }

    override suspend fun deleteModuleLink(id: Int): Boolean = DatabaseFactory.dbQuery {
        ModuleLinks.deleteWhere { ModuleLinks.id eq id } > 0
    }
}