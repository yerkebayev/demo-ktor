package com.example.plugins

import com.example.dao.MetaDAOImpl
import com.example.model.Meta
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString

fun Application.configureMetaRouting() {
    val dao = MetaDAOImpl()
    routing {
        route("api/module") {
            get("{id}/metas") {
                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
                val filters = call.request.queryParameters.toMap()
                val pageNumber = call.parameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.parameters["size"]?.toIntOrNull() ?: 10

                val offset = (pageNumber - 1) * pageSize
                val limit = pageSize

                val metasList = dao.getMetas(removeSquareBrackets(filters), moduleId, offset.toLong(), limit)

                call.respondText(jsonContentConverter.encodeToString(metasList), status = HttpStatusCode.OK)

            }
            get("{id}/meta/{mid}") {
                val metaId = call.parameters.getOrFail<Int>("mid").toInt()
                call.respond(jsonContentConverter.encodeToString(dao.meta(metaId)))
            }
            post("{id}/add-meta") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val jsonString = call.receive<String>()
                val meta = jsonContentConverter.decodeFromString(Meta.serializer(), jsonString)
                dao.addNewMeta(id, meta.metaKey, meta.metaValue)
                call.respond(HttpStatusCode.Created)
            }
            put("{id}/meta/{mid}") {
                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
                val metaId = call.parameters.getOrFail<Int>("mid").toInt()
                val jsonString = call.receive<String>()
                val meta = jsonContentConverter.decodeFromString(Meta.serializer(), jsonString)
                when( dao.editMeta(metaId,
                    moduleId,
                    meta.metaKey,
                    meta.metaValue)) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
            delete("{id}/meta/{mid}") {
                val metaId = call.parameters.getOrFail<Int>("mid").toInt()
                dao.deleteMeta(metaId)
                when( dao.deleteMeta(metaId) ) {
                    true -> call.respond(HttpStatusCode.Accepted)
                    false -> call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}