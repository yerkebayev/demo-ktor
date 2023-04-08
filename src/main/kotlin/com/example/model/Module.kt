package com.example.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.sql.Table

@Serializable
data class Module(@Transient val id: Int = 0,
                  var name: String,
                  var type: String,
                  var createdAt: String,
                  var duration: Int,
                  var status: String,
                  var description: String)

object Modules : Table() {
    val id = integer("id").autoIncrement()
    var name = varchar("name", 128)
    var type = varchar("type", 128)
    var createdAt = varchar("createdAt",64)
    var duration = integer("duration")
    var status = varchar("status",128)
    var description = varchar("description", 256)

    override val primaryKey = PrimaryKey(id)
}
