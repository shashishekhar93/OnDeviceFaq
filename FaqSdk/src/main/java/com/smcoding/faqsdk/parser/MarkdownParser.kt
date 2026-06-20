package com.smcoding.faqsdk.parser

import java.io.File

class MarkdownParser {
    fun extractText(file: File): String {
        return try {
            file.readText()
        } catch (e: Exception) {
            ""
        }
    }
}
