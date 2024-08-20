package org.example

fun main() {
    val apiKey = "c73b7343341e45ca9c466e3b670d632b" // replace with you API_KEY
    val gptClient = GPTClient(apiKey)
    val conversationHandler = ConversationHandler(gptClient)
    conversationHandler.start()
}