package com.smcoding.faqsdk.search

class IntentDetector {

    fun isCountQuery(
        query: String
    ): Boolean {

        val lower =
            query.lowercase()

        return lower.contains("how many")
                || lower.contains("total faq")
                || lower.contains("number of faq")
                || lower.contains("count")
    }
}