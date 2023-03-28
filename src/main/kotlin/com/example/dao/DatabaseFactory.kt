package com.example.dao

import com.example.model.Modules
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcURL = "jdbc:mysql://localhost:3306/mydatabase"
        val user = "root"
        val password = "password"
        val database = Database.connect(jdbcURL, driverClassName, user, password)
        transaction(database) {
            SchemaUtils.create(Modules)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
