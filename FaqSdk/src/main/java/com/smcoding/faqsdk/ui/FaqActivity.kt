package com.smcoding.faqsdk.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smcoding.faqsdk.R
import com.smcoding.faqsdk.model.Message

class FaqActivity : AppCompatActivity() {

    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        val rvChat: RecyclerView = findViewById(R.id.rvChat)
        val etQuestion: EditText = findViewById(R.id.etQuestion)
        val btnSend: Button = findViewById(R.id.btnSend)

        adapter = ChatAdapter()
        rvChat.adapter = adapter
        rvChat.layoutManager = LinearLayoutManager(this)

        btnSend.setOnClickListener {
            val question = etQuestion.text.toString()
            if (question.isBlank()) return@setOnClickListener

            adapter.add(Message(question, true))
            etQuestion.text.clear()

            /*lifecycleScope.launch {
                val answer = FaqSdk.ask(question)
                adapter.add(Message(answer, false))
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }*/
        }
    }
}
