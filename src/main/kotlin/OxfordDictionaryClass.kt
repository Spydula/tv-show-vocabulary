package org.example

import okhttp3.OkHttpClient
import okhttp3.Request
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.JsonAdapter
import java.io.IOException

data class ApiResponse(
    val results: List<Result>
) {
    data class Result(
        val lexicalEntries: List<LexicalEntry>
    ) {
        data class LexicalEntry(
            val entries: List<Entry>
        ) {
            data class Entry(
                val senses: List<Sense>
            ) {
                data class Sense(
                    val definitions: List<String>?
                )
            }
        }
    }
}

class OxfordDictionaryClass (private val appId: String, private val appKey: String) {
    fun getWordDefinition(word: String): String? {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://od-api-sandbox.oxforddictionaries.com/api/v2/entries/en-us/$word")
            .addHeader("app_id", appId)
            .addHeader("app_key", appKey)
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val adapter: JsonAdapter<ApiResponse> = moshi.adapter(ApiResponse::class.java)
            return try {
                val apiResponse = adapter.fromJson(responseBody)
                apiResponse?.results?.firstOrNull()?.lexicalEntries?.firstOrNull()?.entries?.firstOrNull()?.senses?.firstOrNull()?.definitions?.firstOrNull()
            } catch (e: Exception) {
                throw IOException("Error parsing JSON response", e)
            }
        }
    }
}
