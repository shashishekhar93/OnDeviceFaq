package com.smcoding.faqsdk.embedding

import ai.onnxruntime.*
import android.content.Context
import android.util.Log

object EmbeddingManager {

    private lateinit var env: OrtEnvironment

    private lateinit var session: OrtSession

    fun initialize(
        context: Context
    ) {

        env = OrtEnvironment.getEnvironment()

        val modelBytes =
            context.assets
                .open("models/model.onnx")
                .readBytes()

        session =
            env.createSession(
                modelBytes
            )

        //MiniLMTokenizer.initialize(context)
    }

    fun session(): OrtSession {
        return session
    }

    fun environment(): OrtEnvironment {
        return env
    }

    fun printModelInfo() {

        session.inputNames.forEach {

            Log.d(
                "ONNX_INPUT",
                it
            )
        }

        session.outputNames.forEach {

            Log.d(
                "ONNX_OUTPUT",
                it
            )
        }

        val outputInfo =
            session.outputInfo["last_hidden_state"]

        Log.d(
            "ONNX_OUTPUT_INFO",
            outputInfo.toString()
        )
    }

    fun printFullModelInfo() {

        session.inputInfo.forEach {

            android.util.Log.d(
                "MODEL_INPUT",
                "${it.key} -> ${it.value.info}"
            )
        }

        session.outputInfo.forEach {

            android.util.Log.d(
                "MODEL_OUTPUT",
                "${it.key} -> ${it.value.info}"
            )
        }
    }
}