package com.example.plugins

import com.example.dao.ModuleLinkDAOImpl
import com.example.model.ModuleLink
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString

fun Application.configureModuleLinkRouting() {
    val dao = ModuleLinkDAOImpl()
    routing {
        route("api/module-link") {
            get {
                val filters = call.request.queryParameters.toMap()
                val pageNumber = call.parameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.parameters["size"]?.toIntOrNull() ?: 10

                val offset = (pageNumber - 1) * pageSize
                val limit = pageSize

                val moduleLinksList = dao.getModuleLinks(removeSquareBrackets(filters), offset.toLong(), limit)

                call.respondText(jsonContentConverter.encodeToString(moduleLinksList), status = HttpStatusCode.OK)

            }
            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }
                val moduleLink = dao.moduleLink(id)

                if (moduleLink == null) {
                    call.respond(HttpStatusCode.BadRequest, "ModuleLink not found for ID: $id")
                    return@get
                }
                call.respondText(jsonContentConverter.encodeToString(moduleLink), status = HttpStatusCode.OK)
            }
            post {
                val jsonString = call.receive<String>()
                val moduleLink = jsonContentConverter.decodeFromString(ModuleLink.serializer(), jsonString)
                dao.addNewModuleLink(moduleLink.parentId, moduleLink.childId, moduleLink.linkType)
                call.respond(HttpStatusCode.Created)
            }
            put("{id}") {
                val jsonString = call.receive<String>()
                val moduleLink = jsonContentConverter.decodeFromString(ModuleLink.serializer(), jsonString)
                when (dao.editModuleLink(moduleLink.id, moduleLink.parentId, moduleLink.childId, moduleLink.linkType)) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
            delete("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                when (dao.deleteModuleLink(id)) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}