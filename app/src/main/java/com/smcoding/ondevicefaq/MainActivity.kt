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
import com.smcoding.faqsdk.core.KnowledgeSdk
import com.smcoding.faqsdk.llm.KnowledgeConfig
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
            KnowledgeSdk.importPdf(tempFile)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize FAQ SDK
        KnowledgeSdk.initialize(applicationContext, KnowledgeConfig())
        
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
