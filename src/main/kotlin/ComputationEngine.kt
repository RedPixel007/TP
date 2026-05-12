package com.example.engine

import com.example.DTO.ComputeRequest
import com.example.DTO.ComputeResult
import com.example.storage.ResultStorage
import kotlinx.coroutines.*
import kotlin.math.*

object ComputationEngine {
    // собственный scope — движок живёт независимо от запросов
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private fun evalFunction(name: String, x: Double): Double = when (name) {
        "SIN"    -> sin(x)
        "COS"    -> cos(x)
        "SIN_X2" -> sin(x * x)
        "X2"     -> x * x
        else     -> x
    }

    private fun integrate(req: ComputeRequest): Double {
        val n = if (req.n % 2 == 0) req.n else req.n + 1
        val h = (req.b - req.a) / n
        var sum = evalFunction(req.function, req.a) + evalFunction(req.function, req.b)

        for (i in 1 until n) {
            val x = req.a + i * h
            sum += if (i % 2 == 0) 2 * evalFunction(req.function, x)
            else             4 * evalFunction(req.function, x)
        }
        return sum * h / 3.0
    }

    fun submit(id: String, request: ComputeRequest) {
        scope.launch {
            // тяжёлые вычисления в пуле потоков — не блокируем сервер
            val value = withContext(Dispatchers.Default) {
                integrate(request)
            }
            ResultStorage.save(
                ComputeResult(id = id, status = "DONE", result = value)
            )
        }
    }
}