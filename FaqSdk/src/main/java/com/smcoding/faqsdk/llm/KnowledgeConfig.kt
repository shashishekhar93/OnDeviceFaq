package com.smcoding.faqsdk.llm

data class KnowledgeConfig(
    val modelPath: String? = null,
    val topK: Int = 5,
    val maxContextChunks: Int = 5,
    val maxOutputTokens: Int = 256
)