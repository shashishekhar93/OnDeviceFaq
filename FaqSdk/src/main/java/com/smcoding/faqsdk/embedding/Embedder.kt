package com.smcoding.faqsdk.embedding

interface Embedder {

    fun embed(
        text: String
    ): FloatArray
}