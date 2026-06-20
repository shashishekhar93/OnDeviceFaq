package com.smcoding.faqsdk.retrieval

class Chunker {

    companion object {

        private const val CHUNK_SIZE = 800

        private const val OVERLAP = 150
    }

    fun chunk(
        text: String
    ): List<String> {

        if (text.isBlank()) {
            return emptyList()
        }

        val chunks =
            mutableListOf<String>()

        var start = 0

        while (start < text.length) {

            val end =
                minOf(
                    start + CHUNK_SIZE,
                    text.length
                )

            chunks.add(
                text.substring(
                    start,
                    end
                )
            )

            start +=
                CHUNK_SIZE - OVERLAP
        }

        return chunks
    }
}