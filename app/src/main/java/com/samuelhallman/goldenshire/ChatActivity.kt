package com.samuelhallman.goldenshire

import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ChatActivity : AppCompatActivity() {
    private val API_KEY = ""

    private val API_URL = "https://api.groq.com/openai/v1/chat/completions"
    private val MODEL = "llama-3.3-70b-versatile"

    private lateinit var responseText: TextView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    private val client = OkHttpClient()

    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(R.layout.activity_chat)

        responseText = findViewById(R.id.responseText)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val userMessage = messageInput.text.toString().trim()

            if (userMessage.isEmpty()) {
                Toast.makeText(this, "Please type a message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            responseText.text = "Thinking..."
            sendButton.isEnabled = false

            sendMessageToAI(userMessage)
        }
    }
    private  fun sendMessageToAI(userMessage: String) {
        val messagesArray = JSONArray()
        val userMsg = JSONObject()
        userMsg.put("role", "user")
        userMsg.put("content", userMessage)
        messagesArray.put(userMsg)

        val jsonBody = JSONObject()
        jsonBody.put("model", MODEL)
        jsonBody.put("messages", messagesArray)

        val body = jsonBody.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    responseText.text = "Error: ${e.message}"
                    sendButton.isEnabled = true
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                runOnUiThread {
                    sendButton.isEnabled = true

                    if (!response.isSuccessful || responseBody == null) {
                        responseText.text = "Error: ${response.code}"
                        return@runOnUiThread
                    }

                    try {
                        val json = JSONObject(responseBody)
                        val choices = json.getJSONArray("choices")
                        val firstChoice = choices.getJSONObject(0)
                        val message = firstChoice.getJSONObject("message")
                        val content = message.getString("content")

                        responseText.text = content
                    } catch (e: Exception) {
                        responseText.text = "Could not read the reply"
                    }
                }
            }
        })
    }
}