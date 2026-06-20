package com.smcoding.faqsdk.retrieval

import com.smcoding.faqsdk.storage.FaqDao

class Retriever(
    private val dao: FaqDao
) {

    suspend fun retrieve(
        query: String,
        topK: Int = 5
    ): List<SearchResult> {

        val documents =
            dao.getAllFaqs()

        val queryWords =
            query.lowercase()
                .split(" ")
                .filter {
                    it.isNotBlank()
                }

        return documents.map {

            SearchResult(
                id = it.id,
                title = it.title,
                content = it.content,
                score = score(
                    queryWords,
                    "${it.title} ${it.content}"
                )
            )
        }
            .sortedByDescending {
                it.score
            }
            .take(topK)
    }

    private fun score(
        queryWords: List<String>,
        document: String
    ): Float {

        val lower =
            document.lowercase()

        var score = 0f

        queryWords.forEach {

            if (lower.contains(it)) {
                score += 1f
            }
        }

        return score
    }
}