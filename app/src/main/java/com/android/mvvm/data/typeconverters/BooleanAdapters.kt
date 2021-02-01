package com.android.mvvm.data.typeconverters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson
import java.util.*

class BooleanAdapters {

    @ToJson
    fun toJson(boolean: Boolean): Int {
        return if (boolean) 1 else 0
    }

    @FromJson
    fun fromJson(reader: JsonReader): Boolean {
        return when(reader.peek()) {
            JsonReader.Token.STRING -> {
                val bool = reader.nextString().toLowerCase(Locale.getDefault())
                return bool == "true"
            }
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            JsonReader.Token.NUMBER -> {
                val int = reader.nextInt()
                return int == 1
            }
            else -> false
        }
    }
}