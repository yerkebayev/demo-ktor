package com.example.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.sql.Table

@Serializable
data class Meta (@Transient val id: Int = 0,
                 @Transient val moduleId: Int = 0,
                 val metaKey: String,
                 val metaValue: String)

object Metas: Table() {
    val id = integer("metaId").autoIncrement()
    val moduleId = integer("moduleId").references(Modules.moduleId)
    val metaKey = varchar("metaKey", 128)
    val metaValue = varchar("metaValue", 256)

    override val primaryKey = PrimaryKey(id)
}
