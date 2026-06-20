package com.smcoding.faqsdk.embedding.legacy

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import android.content.Context
import java.io.File

object MiniLMTokenizer {

    private lateinit var tokenizer: HuggingFaceTokenizer

    fun initialize(
        context: Context
    ) {

        val tokenizerFile =
            File(
                context.filesDir,
                "tokenizer.json"
            )

        if (!tokenizerFile.exists()) {

            context.assets.open(
                "models/tokenizer.json"
            ).use { input ->

                tokenizerFile.outputStream()
                    .use { output ->
                        input.copyTo(output)
                    }
            }
        }

        tokenizer =
            HuggingFaceTokenizer.newInstance(
                tokenizerFile.toPath()
            )
    }

    data class TokenResult(

        val inputIds: LongArray,

        val attentionMask: LongArray,

        val tokenTypeIds: LongArray
    )

    fun tokenize(
        text: String
    ): TokenResult {

        val encoding =
            tokenizer.encode(text)

        return TokenResult(
            inputIds = encoding.ids,
            attentionMask = encoding.attentionMask,
            tokenTypeIds = encoding.typeIds
        )
    }
}