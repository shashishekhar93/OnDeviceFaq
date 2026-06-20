package com.smcoding.faqsdk.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.smcoding.faqsdk.debug.OrtxSmokeTest
import com.smcoding.faqsdk.embedding.EmbeddingConverter
import com.smcoding.faqsdk.embedding.EmbeddingManager
import com.smcoding.faqsdk.embedding.ONNXEmbedder
import com.smcoding.faqsdk.parser.FaqExtractor
import com.smcoding.faqsdk.parser.MarkdownParser
import com.smcoding.faqsdk.parser.PdfParser
import com.smcoding.faqsdk.search.SearchEngine
import com.smcoding.faqsdk.storage.FaqChunk
import com.smcoding.faqsdk.storage.FaqDatabase
import com.smcoding.faqsdk.ui.FaqActivity
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

object FaqSdk {
    private lateinit var database: FaqDatabase
    private lateinit var searchEngine: SearchEngine
    private val pdfParser = PdfParser()
    private val markdownParser = MarkdownParser()
    private val scope = CoroutineScope(Dispatchers.IO)

    private val faqExtractor = FaqExtractor()

    fun initialize(context: Context) {
        EmbeddingManager.initialize(context)
        PDFBoxResourceLoader.init(context)
        database = FaqDatabase.getDatabase(context)
        searchEngine = SearchEngine(database.faqDao())
        EmbeddingManager.printModelInfo()
        OrtxSmokeTest.print()
        EmbeddingManager.printFullModelInfo()
    }

    fun importPdf(
        file: File
    ) {

        scope.launch {

            database.faqDao().clearAll()

            val text =
                pdfParser.extractText(file)

            val faqs =
                faqExtractor.extractFaqs(text)

            val embedder =
                ONNXEmbedder()

            val faqChunks =
                faqs.map {

                    val embedding =
                        embedder.embed(
                            "${it.title}\n${it.content}"
                        )

                    Log.d(
                        "VECTOR_SIZE",
                        embedding.size.toString()
                    )

                    Log.d(
                        "VECTOR_SAMPLE",
                        embedding.take(10)
                            .joinToString()
                    )


                    FaqChunk(
                        faqId = it.faqId,
                        title = it.title,
                        content = it.content,
                        embedding =
                            EmbeddingConverter
                                .toJson(embedding)
                    )
                }

            database
                .faqDao()
                .insertAll(faqChunks)
        }


    }

    /*suspend fun ask(question: String): String {
        return searchEngine.search(question)
    }*/

    fun openFaqScreen(activity: Activity) {
        val intent = Intent(activity, FaqActivity::class.java)
        activity.startActivity(intent)
    }


}
