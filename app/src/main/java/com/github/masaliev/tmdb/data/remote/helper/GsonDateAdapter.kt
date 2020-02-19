package com.github.masaliev.tmdb.data.remote.helper

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GsonDateAdapter : JsonSerializer<Date>,
    JsonDeserializer<Date> {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


    override fun serialize(
        date: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(dateFormat.format(date))

    override fun deserialize(
        jsonElement: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {

        jsonElement?.asString?.let {
            try {
                return dateFormat.parse(it)
            } catch (ignored: ParseException) {

            }
        }
        throw JsonParseException("Unable to parse: $jsonElement")
    }
}