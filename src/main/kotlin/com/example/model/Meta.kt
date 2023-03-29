package com.example.model

import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table

data class Meta (val id: Int,
                 val moduleId: Int,
                 val metaKey: String,
                 val metaValue: String)

object Metas: Table() {
    val id = integer("id").autoIncrement()
    val moduleId = integer("moduleId").references(Modules.id)
    val metaKey = varchar("metaKey", 128)
    val metaValue = varchar("metaValue", 256)

    override val primaryKey = PrimaryKey(id)
}
