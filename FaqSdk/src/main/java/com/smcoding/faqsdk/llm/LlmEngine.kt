package com.smcoding.faqsdk.llm

interface LlmEngine {

    suspend fun generate(
        prompt: String
    ): GenerationResult
}