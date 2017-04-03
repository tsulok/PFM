package com.pinup.pfm.domain.network.utility

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom date serializer
 */
class PFMDateSerializer: TypeAdapter<Date>() {

    private val longDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val shortDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    init {
        longDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        shortDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }


    override fun write(out: JsonWriter?, value: Date?) {
        if (value == null) {
            out?.nullValue()
            return
        }
        // Use short value format when sending dates
        val dateFormatAsString = longDateFormat.format(value)
        out?.value(dateFormatAsString)
    }

    override fun read(`in`: JsonReader?): Date {
        if (`in`?.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return Date(0)
        }

        return deserializeToDate(`in`?.nextString())
    }

    @Synchronized fun deserializeToDate(dateAsString: String?): Date {

        if (dateAsString == null) {
            return Date(0)
        }

        try {
            return Date(dateAsString.toLong())
        } catch (e: Exception) {
            try {
                return this.longDateFormat.parse(dateAsString)
            } catch (longDateFormatParseException: ParseException) {
                try {
                    return this.shortDateFormat.parse(dateAsString)
                } catch (shortDateFormatParseException: ParseException) {
                    return Date(0)
                }
            }
        }
    }
}