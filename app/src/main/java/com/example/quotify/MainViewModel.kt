package com.example.quotify

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.io.InputStream
import kotlin.random.Random

class MainViewModel(private val context: Context) : ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()
    private var index = 0

    init {
        quoteList=loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): Array<Quote> {
        val inputStream: InputStream = context.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer,Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json,Array<Quote>::class.java)
    }


    var currentQuote by mutableStateOf(quoteList[index])

    fun nextQuote() {
        index = (index + 1) % quoteList.size
        currentQuote = quoteList[index]
    }

    fun previousQuote() {
        index = (index - 1 + quoteList.size) % quoteList.size
        currentQuote = quoteList[index]
    }
}