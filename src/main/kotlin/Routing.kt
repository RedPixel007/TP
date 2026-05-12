package com.example.plugins

import com.example.DTO.ComputeRequest
import com.example.DTO.ComputeResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Сервер работает!")
        }

        post("/compute") {
            val request = call.receive<ComputeRequest>()
            // пока просто возвращаем заглушку с данными из запроса
            call.respond(
                HttpStatusCode.Accepted,
                ComputeResult(
                    id = "stub-id",
                    status = "PENDING"
                )
            )
        }

        get("/result/{id}") {
            val id = call.parameters["id"] ?: "unknown"
            call.respond(
                ComputeResult(
                    id = id,
                    status = "DONE",
                    result = 42.0
                )
            )
        }
    }
}