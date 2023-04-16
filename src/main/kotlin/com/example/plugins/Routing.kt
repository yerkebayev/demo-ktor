package com.example.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.util.*

fun Application.configureRouting() {


//    routing {
//        get("/") {
//            call.respondRedirect("modules")
//        }
//        route("modules") {
//            get {
//                call.respond(FreeMarkerContent("index.ftl", mapOf("modules" to dao.allModules())))
//            }
//            get("new") {
//                call.respond(FreeMarkerContent("new.ftl", model = null))
//            }
//            post {
//                val formParameters = call.receiveParameters()
//                val name = formParameters.getOrFail("name")
//                val type = formParameters.getOrFail("type")
//                val createdAt = formParameters.getOrFail("createdAt")
//                val duration = formParameters.getOrFail("duration").toInt()
//                val status = formParameters.getOrFail("status")
//                val description = formParameters.getOrFail("description")
//                dao.addNewModule(name, type, createdAt, duration, status, description)
//                call.respondRedirect("/modules")
//            }
//            get("{id}") {
//                val id = call.parameters.getOrFail<Int>("id").toInt()
//                call.respond(
//                    FreeMarkerContent(
//                        "show.ftl",
//                        mapOf("module" to dao.module(id), "metas" to dao.allMetas(id))
//                    )
//                )
//            }
//            get("{id}/edit") {
//                val id = call.parameters.getOrFail<Int>("id").toInt()
//                call.respond(
//                    FreeMarkerContent(
//                        "edit.ftl",
//                        mapOf("module" to dao.module(id), "metas" to dao.allMetas(id))
//                    )
//                )
//            }
//            post("{id}") {
//                val id = call.parameters.getOrFail<Int>("id").toInt()
//                val formParameters = call.receiveParameters()
//                val action = formParameters.getOrFail("_action")
//                when (action) {
//                    "update" -> {
//                        dao.editModule(
//                            id,
//                            formParameters.getOrFail("name"),
//                            formParameters.getOrFail("type"),
//                            formParameters.getOrFail("createdAt"),
//                            formParameters.getOrFail("duration").toInt(),
//                            formParameters.getOrFail("status"),
//                            formParameters.getOrFail("description")
//                        )
//                        call.respondRedirect("/modules/$id")
//                    }
//                    "delete" -> {
//                        dao.deleteModule(id)
//                        call.respondRedirect("/modules")
//                    }
//                }
//            }
//            get("{id}/new") {
//                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
//                call.respond(FreeMarkerContent("meta_new.ftl", model = mapOf("module" to dao.module(moduleId))))
//            }
//            post("{id}/edit") {
//                val formParameters = call.receiveParameters()
//                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
//                val key = formParameters.getOrFail("key")
//                val value = formParameters.getOrFail("value")
//                dao.addNewMeta(moduleId, key, value)
//                call.respondRedirect("/modules")
//            }
//            get("{id}/{mid}/edit") {
//                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
//                val metaId = call.parameters.getOrFail<Int>("mid").toInt()
//                call.respond(
//                    FreeMarkerContent(
//                        "meta_edit.ftl",
//                        mapOf("module" to dao.module(moduleId), "meta" to dao.meta(metaId))
//                    )
//                )
//            }
//            post("{id}/{mid}") {
//                val moduleId = call.parameters.getOrFail<Int>("id").toInt()
//                val metaId = call.parameters.getOrFail<Int>("mid").toInt()
//                val formParameters = call.receiveParameters()
//                val action = formParameters.getOrFail("_action")
//                when (action) {
//                    "update" -> {
//                        dao.editMeta(
//                            metaId,
//                            moduleId,
//                            formParameters.getOrFail("key"),
//                            formParameters.getOrFail("value"),
//                        )
//                        call.respondRedirect("/modules/$moduleId/edit")
//                    }
//                    "delete" -> {
//                        dao.deleteMeta(metaId)
//                        call.respondRedirect("/modules/$moduleId/edit")
//                    }
//                }
//
//            }
//        }
//    }
}
