package com.smcoding.faqsdk.parser

import com.smcoding.faqsdk.model.ParsedFaq

class FaqExtractor {

    fun extractFaqs(
        rawText: String
    ): List<ParsedFaq> {

        val faqPattern =
            Regex(
                "(FAQ\\s+\\d+\\.\\d+\\s*\\|\\s*.*?)(?=FAQ\\s+\\d+\\.\\d+\\s*\\||$)",
                setOf(
                    RegexOption.DOT_MATCHES_ALL,
                    RegexOption.IGNORE_CASE
                )
            )

        val matches =
            faqPattern.findAll(rawText)

        val result =
            mutableListOf<ParsedFaq>()

        matches.forEach { match ->

            val faqBlock =
                match.value.trim()

            val faqId =
                extractFaqId(faqBlock)

            val title =
                extractTitle(faqBlock)

            val content =
                extractContent(faqBlock)

            result.add(
                ParsedFaq(
                    faqId = faqId,
                    title = title,
                    content = content
                )
            )
        }

        return result
    }

    private fun extractFaqId(
        block: String
    ): String {

        val regex =
            Regex("FAQ\\s+\\d+\\.\\d+")

        return regex.find(block)
            ?.value
            ?: "UNKNOWN"
    }

    private fun extractTitle(
        block: String
    ): String {

        val regex =
            Regex(
                "FAQ\\s+\\d+\\.\\d+\\s*\\|\\s*(.*)"
            )

        return regex.find(block)
            ?.groupValues
            ?.getOrNull(1)
            ?.lineSequence()
            ?.firstOrNull()
            ?.trim()
            ?: "Untitled FAQ"
    }

    private fun extractContent(
        block: String
    ): String {

        val firstNewLine =
            block.indexOf("\n")

        if (firstNewLine == -1) {
            return block
        }

        return block.substring(
            firstNewLine
        ).trim()
    }
}