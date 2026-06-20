package com.smcoding.faqsdk.debug

import android.util.Log

object EmbeddingSmokeTest {

    fun print(
        vector: FloatArray
    ) {

        Log.d(
            "VECTOR_SIZE",
            vector.size.toString()
        )

        Log.d(
            "VECTOR_SAMPLE",
            vector.take(10)
                .joinToString()
        )
    }
}