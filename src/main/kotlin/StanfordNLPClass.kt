package org.example


import edu.stanford.nlp.pipeline.CoreDocument
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.ling.CoreAnnotations

import com.londogard.nlp.stopwords.Stopwords
import com.londogard.nlp.tokenizer.*
import com.londogard.nlp.utils.LanguageSupport.en

import java.util.Properties

class StanfordNLPClass(text: String) {

    private val pipeline: StanfordCoreNLP
    private var wordList: MutableList<String> = mutableListOf()

    private val properNouns: MutableSet<String> = mutableSetOf()
    private val commonWords: MutableMap<String, Int> = mutableMapOf()
    private val stopwords: MutableMap<String, Int> = mutableMapOf()

    init {
        val props = Properties().apply {
            setProperty("annotators", "tokenize,ssplit,pos,lemma,ner")
        }
        pipeline = StanfordCoreNLP(props)
        createWordList(text)
        removeStopwords()
        lemmatizeWordList()
        categorizeWords()
    }

    private fun createWordList(text: String){
        val tokenizedText = SimpleTokenizer().split(text)
        val regex = Regex("^[a-zA-Z'`]+$")
        wordList = tokenizedText.filter { word ->
            regex.matches(word)
        }.toMutableList()
    }

    private fun removeStopwords(){
        val filteredList = mutableListOf<String>()
        wordList.forEach{ word->
            if (Stopwords.isStopword(word, en)){
                stopwords[word] = (stopwords[word] ?: 0) + 1
            }
            else{
                filteredList.add(word)
            }
        }
        wordList = filteredList
    }

    private fun lemmatizeWordList(){
        val document = CoreDocument(wordList.joinToString(" "))
        pipeline.annotate(document)
        wordList = document.tokens().map { it.lemma() }.toMutableList()
    }

    fun categorizeWords(){
        val document = CoreDocument(wordList.joinToString(" "))
        pipeline.annotate(document)

        document.tokens().forEach{ token->
            val nerTag = token.get(CoreAnnotations.NamedEntityTagAnnotation::class.java)
            if(token.word() == token.word().lowercase())
            {
                val word = token.word().lowercase()
                commonWords[word] = (commonWords[word] ?: 0) + 1
            }
            else if (nerTag != "O") {
                properNouns.add(token.word())
            } else {
                val word = token.word().lowercase()
                commonWords[word] = (commonWords[word] ?: 0) + 1
            }
        }
    }

    fun getWordList() : MutableList<String>{
        return wordList
    }

    fun getProperNouns() : MutableSet<String>{
        return properNouns
    }

    fun getCommonWords() : MutableMap<String, Int>{
        return commonWords
    }

    fun getStopwords() : MutableMap<String, Int>{
        return stopwords
    }










}