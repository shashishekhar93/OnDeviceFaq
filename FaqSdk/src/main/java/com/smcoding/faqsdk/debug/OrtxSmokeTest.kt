package com.smcoding.faqsdk.debug

import android.util.Log

object OrtxSmokeTest {

    fun print() {

        val classes = listOf(
            "ai.onnxruntime.extensions.OrtxPackage",
            "ai.onnxruntime.extensions.Tokenizer"
        )

        classes.forEach { className ->

            try {

                Class.forName(className)

                Log.d(
                    "ORTX_CLASS",
                    "$className FOUND"
                )

            } catch (
                e: Exception
            ) {

                Log.d(
                    "ORTX_CLASS",
                    "$className NOT FOUND"
                )
            }
        }
    }
}