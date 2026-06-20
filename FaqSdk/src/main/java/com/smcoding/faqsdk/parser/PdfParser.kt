package com.smcoding.faqsdk.parser

import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File

class PdfParser {

    fun extractText(
        file: File
    ): String {

        return try {

            val document =
                PDDocument.load(file)

            val stripper =
                PDFTextStripper()

            stripper.sortByPosition = true

            val text =
                stripper.getText(document)

            document.close()

            cleanText(text)

        } catch (e: Exception) {

            e.printStackTrace()

            ""
        }
    }

    private fun cleanText(
        text: String
    ): String {

        return text
            .replace("\r", "")
            .replace(Regex("[ ]{2,}"), " ")
            .replace(
                Regex("\n{3,}"),
                "\n\n"
            )
            .trim()
    }
}