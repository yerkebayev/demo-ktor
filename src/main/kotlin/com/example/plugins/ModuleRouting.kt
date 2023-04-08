package com.example.plugins

import com.example.dao.ModuleDAOImpl
import com.example.model.Module
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val jsonContentConverter = Json { ignoreUnknownKeys = true }

fun Application.configureModuleRouting() {
    val dao = ModuleDAOImpl()
    routing {
        route("api/module") {
            get{
                call.respondText(jsonContentConverter.encodeToString(dao.allModules()), status = HttpStatusCode.OK)
            }
            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }
                val module = dao.module(id)
                if (module == null) {
                    call.respond(HttpStatusCode.BadRequest, "Module not found for ID: $id")
                    return@get
                }
                call.respondText(jsonContentConverter.encodeToString(module), status = HttpStatusCode.OK)
            }
            post {
                val jsonString = call.receive<String>()
                val module = jsonContentConverter.decodeFromString(Module.serializer(), jsonString)
                dao.addNewModule(module.name, module.type, module.createdAt, module.duration, module.status, module.description)
                call.respond(HttpStatusCode.Created)
            }
            put("{id}") {
                val jsonString = call.receive<String>()
                val module = jsonContentConverter.decodeFromString(Module.serializer(), jsonString)
                when (dao.editModule(module.id, module.name, module.type, module.createdAt, module.duration, module.status, module.description)) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
            delete("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                when (dao.deleteModule(id)) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
