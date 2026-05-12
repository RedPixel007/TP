package com.example.plugins

import com.example.DTO.ComputeRequest
import com.example.DTO.ComputeResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.storage.ResultStorage
import java.util.UUID
import com.example.engine.ComputationEngine

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Сервер работает!")
        }

        post("/compute") {
            val request = call.receive<ComputeRequest>()
            val id = UUID.randomUUID().toString()

            ResultStorage.save(ComputeResult(id = id, status = "PENDING"))
            ComputationEngine.submit(id, request) // запускается асинхронно, не ждём

            call.respond(HttpStatusCode.Accepted, ComputeResult(id = id, status = "PENDING"))
        }

        get("/result/{id}") {
            val id = call.parameters["id"] ?: run {
                call.respond(HttpStatusCode.BadRequest, "ID не указан")
                return@get
            }

            val result = ResultStorage.get(id) ?: run {
                call.respond(HttpStatusCode.NotFound, "Задача $id не найдена")
                return@get
            }

            call.respond(result)
        }
    }
}