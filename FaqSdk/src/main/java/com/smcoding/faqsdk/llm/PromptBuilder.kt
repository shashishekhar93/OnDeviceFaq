package com.smcoding.faqsdk.llm

class PromptBuilder {

    fun buildPrompt(
        question: String,
        contextChunks: List<String>
    ): String {

        val context =
            contextChunks.joinToString(
                separator = "\n\n"
            )

        return """
            You are a document assistant.

            Answer ONLY from the provided context.

            If the answer is not available in the context,
            say:
            "I could not find the answer in the provided document."

            Context:
            $context

            Question:
            $question

            Answer:
        """.trimIndent()
    }
}