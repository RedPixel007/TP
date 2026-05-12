package com.example.storage

import com.example.DTO.ComputeResult
import java.util.concurrent.ConcurrentHashMap

object ResultStorage {
    // ConcurrentHashMap — потокобезопасный словарик,
    // важно т.к. запросы могут приходить одновременно
    private val store = ConcurrentHashMap<String, ComputeResult>()

    fun save(result: ComputeResult) {
        store[result.id] = result
    }

    fun get(id: String): ComputeResult? {
        return store[id]
    }
}