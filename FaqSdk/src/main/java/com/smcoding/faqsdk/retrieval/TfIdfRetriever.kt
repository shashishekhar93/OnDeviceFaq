package com.smcoding.faqsdk.retrieval

import com.smcoding.faqsdk.storage.DocumentChunk
import com.smcoding.faqsdk.storage.FaqDao
import kotlin.math.ln

class TfIdfRetriever(
    private val dao: FaqDao
) {

    suspend fun retrieve(
        query: String,
        topK: Int = 5
    ): List<SearchResult> {

        val documents =
            dao.getAllFaqs()

        if (documents.isEmpty()) {
            return emptyList()
        }

        val queryTerms =
            tokenize(query)

        val totalDocs =
            documents.size

        return documents.map { document ->

            val score =
                calculateScore(
                    queryTerms = queryTerms,
                    document = document,
                    documents = documents,
                    totalDocs = totalDocs
                )

            SearchResult(
                id = document.id,
                title = document.title,
                content = document.content,
                score = score
            )
        }
            .sortedByDescending {
                it.score
            }
            .take(topK)
    }

    private fun calculateScore(
        queryTerms: List<String>,
        document: DocumentChunk,
        documents: List<DocumentChunk>,
        totalDocs: Int
    ): Float {

        val documentTerms =
            tokenize(
                document.title +
                        " " +
                        document.content
            )

        var score = 0f

        queryTerms.forEach { term ->

            val tf =
                termFrequency(
                    term,
                    documentTerms
                )

            val idf =
                inverseDocumentFrequency(
                    term,
                    documents,
                    totalDocs
                )

            score +=
                (tf * idf)
        }

        return score
    }

    private fun termFrequency(
        term: String,
        terms: List<String>
    ): Float {

        if (terms.isEmpty()) {
            return 0f
        }

        val count =
            terms.count {
                it == term
            }

        return count.toFloat() /
                terms.size
    }

    private fun inverseDocumentFrequency(
        term: String,
        documents: List<DocumentChunk>,
        totalDocs: Int
    ): Float {

        val containingDocs =
            documents.count {

                tokenize(
                    it.title +
                            " " +
                            it.content
                ).contains(term)
            }

        return ln(
            (
                    totalDocs.toFloat() + 1f
                    ) /
                    (
                            containingDocs + 1f
                            )
        )
    }

    private fun tokenize(
        text: String
    ): List<String> {

        return text
            .lowercase()
            .replace(
                Regex("[^a-z0-9 ]"),
                " "
            )
            .split(
                Regex("\\s+")
            )
            .filter {
                it.isNotBlank()
            }
    }

    suspend fun debugQuery(
        query: String
    ) {

        val results =
            retrieve(
                query = query,
                topK = 3
            )

        results.forEachIndexed { index, result ->

            android.util.Log.d(
                "RETRIEVAL",
                """
            Rank: $index
            Score: ${result.score}
            Title: ${result.title}
            Content: ${result.content.take(150)}
            """.trimIndent()
            )
        }
    }

}