package com.smcoding.faqsdk.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "document_chunks")
data class DocumentChunk(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * Example:
     * FAQ 3.1
     */
    val faqId: String,

    /**
     * Example:
     * How Do We Know Humans Are Responsible
     * for Climate Change?
     */
    val title: String,

    /**
     * Complete FAQ content
     */
    val content: String,

    /**
     * Embedding stored as JSON string
     *
     * Example:
     * [0.123,0.456,0.789]
     */
    val embedding: String
)