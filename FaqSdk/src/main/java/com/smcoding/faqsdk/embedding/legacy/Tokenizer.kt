package com.smcoding.faqsdk.embedding.legacy

object Tokenizer {

    private const val MAX_LENGTH = 256

    fun tokenize(
        text: String
    ): LongArray {

        val tokens =
            text.lowercase()
                .split("\\s+".toRegex())
                .take(MAX_LENGTH)

        val ids =
            LongArray(MAX_LENGTH)

        tokens.forEachIndexed { index, token ->

            ids[index] =
                token.hashCode()
                    .toLong()
                    .and(0x7FFFFFFF)
        }

        return ids
    }
}