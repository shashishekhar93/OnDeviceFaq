package com.smcoding.faqsdk.retrieval

class Chunker {

    companion object {

        private const val MAX_CHUNK_SIZE = 1000
    }

    fun chunk(
        text: String
    ): List<String> {

        val paragraphs =
            text.split(
                Regex("\\n\\s*\\n")
            )

        val chunks =
            mutableListOf<String>()

        val builder =
            StringBuilder()

        paragraphs.forEach { paragraph ->

            if (
                builder.length +
                paragraph.length >
                MAX_CHUNK_SIZE
            ) {

                chunks.add(
                    builder.toString()
                        .trim()
                )

                builder.clear()
            }

            builder.append(
                paragraph
            )

            builder.append(
                "\n\n"
            )
        }

        if (
            builder.isNotBlank()
        ) {

            chunks.add(
                builder.toString()
                    .trim()
            )
        }

        return chunks
    }
}