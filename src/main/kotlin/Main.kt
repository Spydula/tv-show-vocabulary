package org.example

import com.londogard.nlp.stemmer.Stemmer
import com.londogard.nlp.utils.LanguageSupport.*

import com.londogard.nlp.stopwords.Stopwords

import com.londogard.nlp.tokenizer.*

import java.io.File

class ListIsEmpty: Exception("List is null")

fun splitText(text: String) : List<String> {
    val tokenizedText = SimpleTokenizer().split(text)
    val regex = Regex("^[a-zA-Z'`]+$")
    val filteredWords = tokenizedText.filter { word ->
        regex.matches(word)
    }.map{ it.lowercase() }
    return filteredWords
}

fun stopwordsCollection(text: List<String>, stopWordsMap: MutableMap<String, Int>) : List<String> {
    val textWithoutStopwords = text.filter{ word ->
        if (Stopwords.isStopword(word, en)){
            stopWordsMap[word] = (stopWordsMap[word] ?: 0) + 1
            false
        }
        else{
            true
        }
    }
    return textWithoutStopwords
}

fun wordStatistic(filteredText: List<String>, WordsMap: MutableMap<String, Int>) {
    val stemmer = Stemmer(en)
    filteredText.forEach() { word->
        WordsMap[stemmer.stem(word)] = (WordsMap[stemmer.stem(word)] ?: 0) + 1
    }
}



fun main() {
    val file = File("alice.txt")
    val text = file.readText(Charsets.US_ASCII)

    val splitText = splitText(text)
    val stopWordsMap = mutableMapOf<String, Int>() // map of Stopwords

    val filteredText = stopwordsCollection(splitText, stopWordsMap) // list of text without stopwords and lowercased

    val WordsMap = mutableMapOf<String, Int>()

    wordStatistic(filteredText, WordsMap)

    println(WordsMap.toList().sortedByDescending { it.second })

    //println(SentencePieceTokenizer.fromLanguageSupportOrNull(sv)?.split("hej där borta, hur mår ni?"))
}
