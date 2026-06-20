package com.smcoding.faqsdk.llm

data class GenerationResult(

    val answer: String,

    val confidence: Float,

    val sources: List<String> = emptyList(),

    val generatedAt: Long =
        System.currentTimeMillis()
)