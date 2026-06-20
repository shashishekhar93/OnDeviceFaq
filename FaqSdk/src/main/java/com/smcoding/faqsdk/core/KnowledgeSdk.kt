package com.smcoding.faqsdk.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.smcoding.faqsdk.llm.GenerationResult
import com.smcoding.faqsdk.llm.KnowledgeConfig
import com.smcoding.faqsdk.llm.KnowledgeEngine
import com.smcoding.faqsdk.parser.MarkdownParser
import com.smcoding.faqsdk.parser.PdfParser
import com.smcoding.faqsdk.retrieval.Chunker
import com.smcoding.faqsdk.retrieval.Retriever
import com.smcoding.faqsdk.retrieval.TfIdfRetriever
import com.smcoding.faqsdk.storage.DocumentChunk
import com.smcoding.faqsdk.storage.FaqDatabase
import com.smcoding.faqsdk.ui.FaqActivity
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

object KnowledgeSdk {

    private lateinit var database: FaqDatabase

    private lateinit var config: KnowledgeConfig

    private lateinit var retriever: Retriever

    private lateinit var knowledgeEngine: KnowledgeEngine

    private val pdfParser = PdfParser()

    private val markdownParser = MarkdownParser()

    private val chunker = Chunker()

    private val scope =
        CoroutineScope(
            Dispatchers.IO
        )

    fun initialize(
        context: Context,
        config: KnowledgeConfig
    ) {

        this.config = config

        PDFBoxResourceLoader.init(
            context
        )

        database =
            FaqDatabase.getDatabase(
                context
            )

        retriever =
            Retriever(
                database.faqDao()
            )

        /*
         * KnowledgeEngine
         * will be initialized
         * after GemmaEngine
         * is implemented
         */
    }

    fun importPdf(
        file: File
    ) {

        scope.launch {

            database
                .faqDao()
                .clearAll()

            val text =
                pdfParser.extractText(
                    file
                )

            val chunks =
                chunker.chunk(
                    text
                )

            val documentChunks =
                chunks.mapIndexed { index, chunk ->

                    DocumentChunk(

                        faqId =
                            "DOC_$index",

                        title =
                            "Chunk $index",

                        content =
                            chunk,

                        embedding =
                            ""
                    )
                }

            database
                .faqDao()
                .insertAll(
                    documentChunks
                )

            //debugPrintChunks()
            val retriever =
                TfIdfRetriever(
                    database.faqDao()
                )

            retriever.debugQuery(
                "climate change"
            )
        }
    }

    /*fun importMarkdown(
        file: File
    ) {

        scope.launch {

            database
                .faqDao()
                .clearAll()

            val text =
                markdownParser.parse(
                    file
                )

            val chunks =
                chunker.chunk(
                    text
                )

            val documentChunks =
                chunks.mapIndexed { index, chunk ->

                    DocumentChunk(

                        faqId =
                            "DOC_$index",

                        title =
                            "Chunk $index",

                        content =
                            chunk,

                        embedding =
                            ""
                    )
                }

            database
                .faqDao()
                .insertAll(
                    documentChunks
                )
        }
    }*/

    suspend fun ask(
        question: String
    ): GenerationResult {

        check(
            ::knowledgeEngine.isInitialized
        ) {
            "KnowledgeEngine not initialized"
        }

        return knowledgeEngine.ask(
            question
        )
    }

    fun setKnowledgeEngine(
        engine: KnowledgeEngine
    ) {

        knowledgeEngine = engine
    }

    fun openKnowledgeScreen(
        activity: Activity
    ) {

        activity.startActivity(
            Intent(
                activity,
                FaqActivity::class.java
            )
        )
    }

    fun debugPrintChunks() {

        scope.launch {

            val chunks =
                database
                    .faqDao()
                    .getAllFaqs()

            android.util.Log.d(
                "CHUNK_COUNT",
                chunks.size.toString()
            )

            chunks.forEachIndexed { index, chunk ->

                android.util.Log.d(
                    "CHUNK_$index",
                    chunk.content.take(100)
                )
            }
        }
    }
}