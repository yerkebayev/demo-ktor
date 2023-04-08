package com.example

import com.example.dao.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*
import com.typesafe.config.ConfigFactory
import com.example.plugins.configureModuleLinkRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun main(args: Array<String>) {
    EngineMain.main(args)
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true
                coerceInputValues = true
                isLenient = true
            })
        }
        // other features and routing code go here
    }
    server.start(wait = true)

}
fun Application.module() {
    DatabaseFactory.init(HoconApplicationConfig(ConfigFactory.load()))
    configureTemplating()
    configureModuleRouting()
    configureMetaRouting()
    configureModuleLinkRouting()
}
