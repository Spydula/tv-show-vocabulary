package org.example

import java.io.File

fun main() {
    val lines = File("gpt_api_key.txt").readLines()
    val apiKey = lines[0] // replace with you API_KEY
    val gptClient = GPTClient(apiKey)
    val conversationHandler = ConversationHandler(gptClient)
    conversationHandler.start()
}