package com.example.model

import com.example.enums.Status
import com.example.enums.StatusSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.sql.Table

@Serializable
data class Module(@Transient val id: Int = 0,
                  var name: String,
                  var type: String,
                  var createdAt: String,
                  var duration: Int,
                  @Serializable(with = StatusSerializer::class)
                  @SerialName("status") var status: Status,
                  var description: String)

object Modules : Table() {
    val moduleId = integer("moduleId").autoIncrement()
    var name = varchar("name", 128)
    var type = varchar("type", 128)
    var createdAt = varchar("createdAt",64)
    var duration = integer("duration")
    var status = varchar("status", 64)
    var description = varchar("description", 256)

    override val primaryKey = PrimaryKey(moduleId)
}

val types = ("PROGRAM, MODULE") // for type column
