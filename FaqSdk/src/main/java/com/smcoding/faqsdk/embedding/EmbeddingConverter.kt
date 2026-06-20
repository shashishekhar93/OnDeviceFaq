package com.smcoding.faqsdk.embedding

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EmbeddingConverter {

    private val gson = Gson()

    fun toJson(
        embedding: FloatArray
    ): String {

        return gson.toJson(
            embedding.toList()
        )
    }

    fun fromJson(
        json: String
    ): FloatArray {

        val type =
            object : TypeToken<List<Float>>() {}
                .type

        val list: List<Float> =
            gson.fromJson(json, type)

        return list.toFloatArray()
    }
}