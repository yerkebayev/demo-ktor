package com.example.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.sql.Table


@Serializable
data class ModuleLink(@Transient val id: Int = 0, val parentId: Int, val childId: Int, val linkType: String)



object ModuleLinks: Table() {
    val id = integer("id").autoIncrement()
    val parentId = integer("parentId")
    val childId = integer("childId")
    val linkType = varchar("linkType", 128)
    override val primaryKey = PrimaryKey(id)
}