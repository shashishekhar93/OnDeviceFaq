package com.smcoding.faqsdk.llm

import com.smcoding.faqsdk.retrieval.Retriever

class KnowledgeEngine(
    private val retriever: Retriever,
    private val llmEngine: LlmEngine,
    private val promptBuilder: PromptBuilder
) {

    suspend fun ask(
        question: String
    ): GenerationResult {

        val chunks =
            retriever.retrieve(
                query = question,
                topK = 5
            )

        val prompt =
            promptBuilder.buildPrompt(
                question = question,
                contextChunks =
                    chunks.map {
                        it.content
                    }
            )

        return llmEngine.generate(
            prompt
        )
    }
}