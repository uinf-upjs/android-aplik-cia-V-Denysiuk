package com.example.booktracker.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object BookJsonLoader {
    fun loadBooksFromJson(context: Context): List<BookEntity> {
        val inputStream = context.assets.open("books.json")
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<BookEntity>>() {}.type
        return Gson().fromJson(reader, type)
    }
}
