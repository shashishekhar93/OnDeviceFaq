package com.smcoding.faqsdk.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FaqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chunk: FaqChunk)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chunks: List<FaqChunk>)

    @Query("SELECT * FROM faq_chunks")
    suspend fun getAllFaqs(): List<FaqChunk>

    @Query(
        """
        SELECT * FROM faq_chunks
        WHERE faqId = :faqId
        LIMIT 1
        """
    )
    suspend fun getFaqById(
        faqId: String
    ): FaqChunk?

    @Query(
        """
        SELECT * FROM faq_chunks
        WHERE title LIKE '%' || :query || '%'
        """
    )
    suspend fun searchByTitle(
        query: String
    ): List<FaqChunk>

    @Query(
        """
        SELECT COUNT(*) 
        FROM faq_chunks
        """
    )
    suspend fun getFaqCount(): Int

    @Query("DELETE FROM faq_chunks")
    suspend fun clearAll()
}