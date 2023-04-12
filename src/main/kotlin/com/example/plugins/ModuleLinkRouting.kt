package com.example.plugins

import Page
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
            get ("{page}/{size}"){
                val filters = call.request.queryParameters.toMap()
                val pageNumber = call.parameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.parameters["size"]?.toIntOrNull() ?: 10

                val offset = (pageNumber - 1) * pageSize.toLong()
                val limit = pageSize

                val filtered = dao.getModuleLinksWithFilters(removeSquareBrackets(filters))
                val totalItems = filtered.size
                val totalPages = (totalItems / limit) + if (totalItems % limit == 0) 0 else 1

                val currentPage = when {
                    totalItems == 0 -> 0
                    pageNumber > totalPages -> totalPages
                    else -> pageNumber
                }

                val paginated =
                    if (limit >= totalItems || offset >= totalItems) filtered
                    else dao.getWithPagination(filtered, offset, limit)

                val page = Page(paginated, currentPage, totalItems, totalPages)
                val json = Page.toJson(page)
                call.respondText(json, ContentType.Application.Json, HttpStatusCode.OK)
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