package com.smcoding.faqsdk.embedding

import android.content.Context

object EmbeddingManager {

    private lateinit var embedder: Embedder

    fun initialize(
        context: Context
    ) {

        embedder =
            BgeEmbedder(
                context
            )
    }

    fun embedder(): Embedder {
        return embedder
    }
}