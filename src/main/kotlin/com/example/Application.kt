package com.example

import com.example.dao.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureTemplating()
    configureRouting()
}
