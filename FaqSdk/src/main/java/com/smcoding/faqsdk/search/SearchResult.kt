package com.smcoding.faqsdk.search

data class SearchResult(

    val faqId: String,

    val title: String,

    val content: String,

    val score: Float
)