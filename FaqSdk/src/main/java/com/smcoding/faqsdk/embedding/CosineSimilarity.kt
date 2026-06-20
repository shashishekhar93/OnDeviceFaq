package com.smcoding.faqsdk.embedding

import kotlin.math.sqrt

object CosineSimilarity {

    fun calculate(
        a: FloatArray,
        b: FloatArray
    ): Float {

        var dot = 0f
        var normA = 0f
        var normB = 0f

        for (i in a.indices) {

            dot += a[i] * b[i]

            normA += a[i] * a[i]

            normB += b[i] * b[i]
        }

        return dot /
                (
                        sqrt(normA) *
                                sqrt(normB)
                        )
    }
}