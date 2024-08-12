package org.example
import java.io.File

fun printStatistics (wordList: List<Pair<String, Int>>) {
    for(index in wordList.indices)
    {
        val word = wordList[index].first
        val count = wordList[index].second
        println("$word: $count")
    }
}

fun main() {
    val file = File("alice.txt")
    val text = file.readText(Charsets.US_ASCII)

    val wordFrequency = mutableMapOf<String, Int>()

    val words = text.split(Regex("\\W+")).filter { it.isNotBlank() }

    words.forEach { word ->
        val re = Regex("[^A-Za-z0-9 ]")
        val cleanWord = re.replace(word, "")

        val lowercaseWord = cleanWord.lowercase()

        if(lowercaseWord.any{ it.isLetter() } ){
            wordFrequency[lowercaseWord] = (wordFrequency[lowercaseWord] ?: 0) + 1
        }
    }

    val sortedWords = wordFrequency.toList().sortedByDescending { it.second }

    printStatistics(sortedWords)
}