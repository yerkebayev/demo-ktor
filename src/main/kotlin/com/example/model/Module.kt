package com.example.model

import org.jetbrains.exposed.sql.Table
import java.util.Date

data class Module(val id: Int,
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
//    var createdAt = Date("createdAt")
    var duration = integer("duration")
    var status = varchar("status",128)
    var description = varchar("description", 256)

    override val primaryKey = PrimaryKey(id)
}
