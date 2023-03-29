package com.example

import com.example.dao.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(HoconApplicationConfig(ConfigFactory.load()))
    configureTemplating()
    configureRouting()
}
