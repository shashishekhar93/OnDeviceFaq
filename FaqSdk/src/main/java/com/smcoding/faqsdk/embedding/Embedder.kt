package com.smcoding.faqsdk.embedding

interface Embedder {

    suspend fun embed(
        text: String
    ): FloatArray
}