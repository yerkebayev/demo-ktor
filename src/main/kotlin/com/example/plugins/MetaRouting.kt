package com.example.plugins

import com.example.dao.MetaDAOImpl
import com.example.model.Meta
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.serialization.encodeToString

fun Application.configureMetaRouting() {
    val dao = MetaDAOImpl()
    routing {
        route("api/module") {
            get("{id}/metas") {
                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(jsonContentConverter.encodeToString(dao.allMetas(moduleId)))
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