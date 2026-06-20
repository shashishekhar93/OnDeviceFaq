package com.smcoding.faqsdk.embedding

class Chunker {
    fun split(text: String): List<String> {
        return text.chunked(500)
    }
}
