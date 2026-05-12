package com.example.DTO

import kotlinx.serialization.Serializable

@Serializable
data class ComputeResult(
    val id: String,
    val status: String,   // "PENDING" или "DONE"
    val result: Double? = null
)