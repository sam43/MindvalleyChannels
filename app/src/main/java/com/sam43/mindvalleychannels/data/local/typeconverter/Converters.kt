package com.sam43.mindvalleychannels.data.local.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sam43.mindvalleychannels.data.remote.Data
import com.sam43.mindvalleychannels.utils.parser.JsonParser


@ProvidedTypeConverter
class Converters(private val jsonParser: JsonParser) {
    @TypeConverter
    fun fromDataJson(json: String): Data? {
        return jsonParser.fromJson<Data>(
            json,
            object : TypeToken<Data>(){}.type
        )
    }

    @TypeConverter
    fun toDataJson(meanings: Data): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<Data>(){}.type
        ) ?: ""
    }
}