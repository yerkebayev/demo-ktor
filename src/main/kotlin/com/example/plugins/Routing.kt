package com.example.plugins

import com.example.dao.dao
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondRedirect("modules")
        }
        route("modules") {
            get {
                call.respond(FreeMarkerContent("index.ftl", mapOf("modules" to dao.allModules())))
            }
            get("new") {
                call.respond(FreeMarkerContent("new.ftl", model = null))

            }
            post {
                val formParameters = call.receiveParameters()
                val name = formParameters.getOrFail("name")
                val type = formParameters.getOrFail("type")
                val createdAt = formParameters.getOrFail("createdAt")
                val duration = formParameters.getOrFail("duration").toInt()
                val status = formParameters.getOrFail("status")
                val description = formParameters.getOrFail("description")
                dao.addNewModule(name, type, createdAt, duration, status, description)
                call.respondRedirect("/modules")
            }
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("module" to dao.module(id))))
            }
            get("{id}/edit") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("edit.ftl", mapOf("module" to dao.module(id))))
            }
            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                val action = formParameters.getOrFail("_action")
                when (action) {
                    "update" -> {
                        dao.editModule(
                            id,
                            formParameters.getOrFail("name"),
                            formParameters.getOrFail("type"),
                            formParameters.getOrFail("createdAt"),
                            formParameters.getOrFail("duration").toInt(),
                            formParameters.getOrFail("status"),
                            formParameters.getOrFail("description")
                        )
                        call.respondRedirect("/modules/$id")
                    }
                    "delete" -> {
                        dao.deleteModule(id)
                        call.respondRedirect("/modules")
                    }
                }
            }
        }
    }
}
