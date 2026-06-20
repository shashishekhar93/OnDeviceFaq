package com.smcoding.faqsdk.search

import com.smcoding.faqsdk.storage.FaqDao

class SearchEngine(private val faqDao: FaqDao) {

  /*  suspend fun search(query: String): String {
        val chunks = faqDao.getAllChunks()
        return chunks.maxByOrNull {
            similarity(query, it.content)
        }?.content ?: "No answer found."
    }*/

    private fun similarity(query: String, text: String): Int {
        val words = query.lowercase().split(" ")
        return words.count {
            text.lowercase().contains(it)
        }
    }
}
