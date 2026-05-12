package com.example.DTO

import kotlinx.serialization.Serializable

@Serializable
data class ComputeRequest(
    val function: String,  // например "INTEGRATE"
    val a: Double,         // левая граница
    val b: Double,         // правая граница
    val n: Int             // число разбиений
)