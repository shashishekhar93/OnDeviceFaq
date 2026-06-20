package com.smcoding.ondevicefaq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.util.Log
import com.smcoding.faqsdk.core.FaqSdk
import com.smcoding.faqsdk.embedding.legacy.ONNXEmbedder
import com.smcoding.faqsdk.parser.FaqExtractor
import com.smcoding.faqsdk.parser.PdfParser
import com.smcoding.ondevicefaq.ui.theme.OnDeviceFaqTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    private val pdfPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val tempFile = File(cacheDir, "imported_faq.pdf")
            contentResolver.openInputStream(it)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            // Test Utility for extraction
            val pdfParser = PdfParser()
            val faqExtractor = FaqExtractor()
            val text = pdfParser.extractText(tempFile)
            val faqs = faqExtractor.extractFaqs(text)
            Log.d("FAQ_TEST", "Total FAQs = ${faqs.size}")

            FaqSdk.importPdf(tempFile)
            FaqSdk.openFaqScreen(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vector =
            ONNXEmbedder()
                .embed(
                    "How do I reset password?"
                )

        Log.d(
            "VECTOR_SIZE",
            vector.size.toString()
        )



        val vocab =
            assets.open("models/vocab.txt")
                .bufferedReader()
                .readLines()

        Log.d(
            "VOCAB_SIZE",
            vocab.size.toString()
        )
        // Initialize FAQ SDK
        FaqSdk.initialize(applicationContext)
        
        enableEdgeToEdge()
        setContent {
            OnDeviceFaqTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        FaqButton(
                            onOpenFaq = {
                                pdfPickerLauncher.launch("application/pdf")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FaqButton(onOpenFaq: () -> Unit) {
    Button(onClick = onOpenFaq) {
        Text(text = "Open FAQ Assistant")
    }
}
