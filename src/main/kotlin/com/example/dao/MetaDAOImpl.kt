package com.example.dao

import com.example.StudentEachTeacher
import com.example.dao.DatabaseFactory.dbQuery
import com.example.dao.DatabaseFactory.jdbcURL
import com.example.dao.DatabaseFactory.password
import com.example.dao.DatabaseFactory.username
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

    override suspend fun studentsEachTeacher(): List<StudentEachTeacher> {
        val list = mutableListOf<StudentEachTeacher>()
        dbQuery {
            val conn = DatabaseFactory.createHikariDataSource(jdbcURL, username, password).connection
            val statement = conn.prepareStatement(
                "SELECT m.metaValue as teacher, m.metaId as id, COUNT(DISTINCT s.metaValue) as num_students_taught " +
                        "FROM mydatabase.Metas m " +
                        "JOIN mydatabase.Metas s ON m.moduleId = s.moduleId " +
                        "WHERE m.metaKey = ? AND s.metaKey = ? " +
                        "GROUP BY m.metaValue, m.metaId;"
            )
            statement.setString(1, "Teacher")
            statement.setString(2, "Student")
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val id: Int = resultSet.getInt("id")
                val name: String = resultSet.getString("teacher")
                val value: Int = resultSet.getInt("num_students_taught")
                val newRow = StudentEachTeacher(id, name, value)
                list.add(newRow)
            }
            resultSet.close()
            statement.close()
            conn.close()
        }
        return list
    }


    override suspend fun howManyTeachersInModule(): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        dbQuery {
            val conn = DatabaseFactory.createHikariDataSource(jdbcURL, username, password).connection
            val statement = conn.prepareStatement(
                "SELECT m.moduleId, m.name as moduleName, COUNT(DISTINCT mt.metaValue) as num_teachers " +
                        "FROM mydatabase.Modules m " +
                        "JOIN mydatabase.Metas mt ON m.moduleId = mt.moduleId " +
                        "WHERE mt.metaKey = 'Teacher' " +
                        "GROUP BY m.moduleId, m.name;"
            )
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val key: String = resultSet.getString("moduleName")
                val value: Int = resultSet.getInt("num_teachers")
                map[key] = value
            }
            resultSet.close()
            statement.close()
            conn.close()
        }
        return map
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