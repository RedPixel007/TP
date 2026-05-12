package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        post("/compute") {
            call.respondText("OK: задача принята (заглушка)")
        }

        get("/result/{id}") {
            val id = call.parameters["id"] ?: "unknown"
            call.respondText("OK: результат для id=$id (заглушка)")
        }
        get("/") {
            call.respondText("Сервер работает!")
        }
    }
}