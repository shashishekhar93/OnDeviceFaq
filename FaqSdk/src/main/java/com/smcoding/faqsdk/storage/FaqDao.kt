package com.smcoding.faqsdk.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FaqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chunk: DocumentChunk)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chunks: List<DocumentChunk>)

    @Query("SELECT * FROM document_chunks")
    suspend fun getAllFaqs(): List<DocumentChunk>

    @Query(
        """
        SELECT * FROM document_chunks
        WHERE faqId = :faqId
        LIMIT 1
        """
    )
    suspend fun getFaqById(
        faqId: String
    ): DocumentChunk?

    @Query(
        """
        SELECT * FROM document_chunks
        WHERE title LIKE '%' || :query || '%'
        """
    )
    suspend fun searchByTitle(
        query: String
    ): List<DocumentChunk>

    @Query("""SELECT COUNT(*)FROM document_chunks""")
    suspend fun getFaqCount(): Int

    @Query("DELETE FROM document_chunks")
    suspend fun clearAll()
}