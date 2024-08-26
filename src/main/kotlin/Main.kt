package org.example

import java.io.File

fun main() {
    val file = File("alice.txt")
    val text = file.readText(Charsets.US_ASCII)

    val textAnalyzer = StanfordNLPClass(text)

    println(textAnalyzer.getWordList())
    println(textAnalyzer.getStopwords())
    println(textAnalyzer.getCommonWords())
    println(textAnalyzer.getProperNouns())
    println(textAnalyzer.getCommonWordsDefinitions())
}

