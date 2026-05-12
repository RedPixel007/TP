package com.example

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.engine.*
import com.example.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}