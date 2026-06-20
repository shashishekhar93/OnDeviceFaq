package com.smcoding.faqsdk.embedding

import android.util.Log

object EmbeddingDebug {

    fun printVector(
        vector: FloatArray
    ) {

        Log.d(
            "VECTOR_SIZE",
            vector.size.toString()
        )

        Log.d(
            "VECTOR_SAMPLE",
            vector.take(10).joinToString()
        )
    }
}