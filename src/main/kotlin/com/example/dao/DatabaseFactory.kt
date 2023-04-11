package com.example.dao

import com.example.loadDataFromFile
import com.example.model.Metas
import com.example.model.ModuleLinks
import com.example.model.Modules
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val jdbcURL = "jdbc:mysql://${config.property("storage.host").getString()}:${config.property("storage.port").getString()}/${config.property("storage.databaseName").getString()}"
        val username = config.property("storage.username").getString()
        val password = config.property("storage.password").getString()
        val database = Database.connect(createHikariDataSource(jdbcURL, username, password))
        transaction(database) {
            SchemaUtils.create(Modules)
            SchemaUtils.create(Metas)
            SchemaUtils.create(ModuleLinks)
        }
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T = //coroutines
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun createHikariDataSource(
        url: String,
        user: String,
        password: String
    ) = HikariDataSource(HikariConfig().apply {
        jdbcUrl = url
        username = user
        this.password = password
        maximumPoolSize = 20
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

}
