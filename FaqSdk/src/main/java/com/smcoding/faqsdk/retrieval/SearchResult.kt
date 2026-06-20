package com.smcoding.faqsdk.retrieval

data class SearchResult(

    val id: Long,

    val title: String,

    val content: String,

    val score: Float
)