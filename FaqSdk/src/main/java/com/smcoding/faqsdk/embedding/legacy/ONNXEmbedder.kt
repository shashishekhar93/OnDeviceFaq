package com.smcoding.faqsdk.embedding.legacy

import ai.onnxruntime.OnnxTensor
import com.smcoding.faqsdk.embedding.Embedder
import com.smcoding.faqsdk.embedding.EmbeddingManager

class ONNXEmbedder : Embedder {

    override fun embed(
        text: String
    ): FloatArray {

        try {

            val tokens =
                MiniLMTokenizer.tokenize(text)

            val env =
                EmbeddingManager.environment()

            val inputIdsTensor =
                OnnxTensor.createTensor(
                    env,
                    arrayOf(tokens.inputIds)
                )

            val attentionMaskTensor =
                OnnxTensor.createTensor(
                    env,
                    arrayOf(tokens.attentionMask)
                )

            val tokenTypeIdsTensor =
                OnnxTensor.createTensor(
                    env,
                    arrayOf(tokens.tokenTypeIds)
                )

            val inputs =
                mapOf(
                    "input_ids" to inputIdsTensor,
                    "attention_mask" to attentionMaskTensor,
                    "token_type_ids" to tokenTypeIdsTensor
                )

            val result =
                EmbeddingManager
                    .session()
                    .run(inputs)

            @Suppress("UNCHECKED_CAST")
            val output =
                result[0].value
                        as Array<Array<FloatArray>>

            return meanPool(
                output[0]
            )

        } catch (
            e: Exception
        ) {

            e.printStackTrace()

            return FloatArray(384)
        }
    }

    private fun meanPool(
        embeddings: Array<FloatArray>
    ): FloatArray {

        if (embeddings.isEmpty()) {
            return FloatArray(384)
        }

        val dimension =
            embeddings[0].size

        val pooled =
            FloatArray(dimension)

        embeddings.forEach { token ->

            for (i in token.indices) {

                pooled[i] += token[i]
            }
        }

        for (i in pooled.indices) {

            pooled[i] /= embeddings.size
        }

        return pooled
    }
}